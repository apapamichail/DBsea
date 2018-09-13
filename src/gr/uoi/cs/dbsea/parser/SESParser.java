package gr.uoi.cs.dbsea.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import gr.uoi.cs.dbsea.sql.Attribute;
import gr.uoi.cs.dbsea.sql.Schema;
import gr.uoi.cs.dbsea.sql.Table;

/**
 * This class uses the parser of the generated ANTLR grammar to parse
 * the file given to the constructor.
 * @author giskou
 */
public class SESParser implements ParserInterface {
	static Schema schema;

	static class UnMach {
		Table orT;
		DDLParser.ForeignContext context;
		
		public UnMach(Table orT, DDLParser.ForeignContext context) {
			this.orT = orT;
			this.context = context;
		}
	}

	/**
	 * 
	 * @param filePath The path of the file to be parsed.
	 * @throws IOException
	 * @throws RecognitionException
	 */
	public static Schema parse(String filePath) {
		CharStream      charStream = null;
		
		try {
			charStream = new ANTLRFileStream(filePath);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		DDLLexer        lexer = new DDLLexer(charStream) ;
		TokenStream     tokenStream = new CommonTokenStream(lexer);
		DDLParser       parser = new DDLParser(tokenStream) ;
		ParseTree       root = parser.start();
		SchemaLoader    loader = new SchemaLoader();
		ParseTreeWalker walker = new ParseTreeWalker();
		walker.walk(loader, root);
		File file = new File(filePath);
		schema.setTitle(file.getName());
		return schema;
	}
	public static void parseSchema(String filePath) {
		CharStream      charStream = null;
		
		try {
			charStream = new ANTLRFileStream(filePath);
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		DDLLexer        lexer = new DDLLexer(charStream) ;
		TokenStream     tokenStream = new CommonTokenStream(lexer);
		DDLParser       parser = new DDLParser(tokenStream) ;
		ParseTree       root = parser.start();
		SchemaLoader    loader = new SchemaLoader();
		ParseTreeWalker walker = new ParseTreeWalker();
		walker.walk(loader, root);
		File file = new File(filePath);
		schema.setTitle(file.getName());
		
	}
	private static class SchemaLoader extends DDLBaseListener {

		private Table table;
		private Attribute a;
		boolean foundTableConst = false;
		boolean foundAlterConst = false;
		boolean foundLineConst = false;
		String alteringTable = null;
		List<UnMach> unMached = new ArrayList<SESParser.UnMach>();

		public void enterStart (DDLParser.StartContext context) {
//			System.out.println("Starting");
			schema = new Schema();
		}
		public void exitStart (DDLParser.StartContext context) {
			processUnmached();
//			System.out.println("\n\n\n" + schema.print());
		}

		public void enterTable (DDLParser.TableContext context) {
			String tableName = removeQuotes(context.table_name().getText());
			table = new Table(tableName);
		}
		public void exitTable (DDLParser.TableContext context) {
			schema.addTable(table);
		}

		public void enterColumn (DDLParser.ColumnContext context) {
			String colName = removeQuotes(context.col_name().getText());
			String colType = context.data_type().getText();
			colType = colType.toUpperCase();
			a = new Attribute(colName, colType);
		}

		public void exitColumn (DDLParser.ColumnContext context) {
			table.addAttribute(a);
		}

		public void enterLine_constraint(DDLParser.Line_constraintContext context) {
			foundLineConst = true;
		}
		public void exitLine_constraint(DDLParser.Line_constraintContext context) {
			foundLineConst = false;
		}

		public void enterAlter_statement(DDLParser.Alter_statementContext context) {
			alteringTable = context.table_name().getText();
		}
		public void exitAlter_statement(DDLParser.Alter_statementContext context) {
			alteringTable = null;
		}

		public void enterTable_constraint(DDLParser.Table_constraintContext context) {
			foundTableConst = true;
		}
		public void exitTable_constraint(DDLParser.Table_constraintContext context) {
			foundTableConst = false;
		}

		public void enterAlter_constraint(DDLParser.Alter_constraintContext context) {
			foundAlterConst = true;
		}
		public void exitAlter_constraint(DDLParser.Alter_constraintContext context) {
			foundAlterConst = true;
		}

		public void enterPrimary (DDLParser.PrimaryContext context) {
			if (foundTableConst) {
				String todo = context.parNameList().getText();
				todo = todo.substring(1, todo.length()-1);
				String[] names = todo.split(",");
				for (String schema : names) {
					if (table.getAttrs().get(schema) != null){
						table.addAttrToPrimeKey(table.getAttrs().get(schema));
					}
				}
			} else if (foundAlterConst) {
			} else if (foundLineConst){
				table.addAttrToPrimeKey(a);
			} else {}
		}

		public void enterForeign (DDLParser.ForeignContext context) {
			Table orTable = null, reTable = null;
			Attribute[] or, re;
			String reTableName = context.reference_definition().table_name().getText();
			if (foundTableConst) {
				orTable = table;
				if (reTableName.compareTo(orTable.getName()) == 0) {
					reTable = table;
				} else {
					reTable = schema.getTables().get(reTableName);
					if (reTable == null) {
						unMached.add(new UnMach(orTable, context));
						return;
					}
				}
			} else if (foundAlterConst) {
//				System.out.println(alteringTable + " " + context.getText());
				orTable = schema.getTables().get(alteringTable);
				reTable = schema.getTables().get(reTableName);
			} else {
				// this is not supposed to happen
			}
			or = getNames(context.parNameList().getText(), orTable);
			re = getNames(context.reference_definition().parNameList().getText(), reTable);
			for (int i = 0; i < or.length; i++) {
				if (or[i] == null || re[i] == null) {
					// sql typo???
					continue;
				}
//				System.out.println(orTable + "." + or[i] + "->" + reTable + "." + re[i] + "\n");
				orTable.getfKey().addReference(or[i], re[i]);
			}
		}

		public void enterReference (DDLParser.ReferenceContext context) {
			Table orTable = table;
			Table reTable = schema.getTables().get(context.reference_definition().table_name().getText());
			Attribute or = a;
			Attribute[] re = getNames(context.reference_definition().parNameList().getText(), reTable);
			orTable.getfKey().addReference(or, re[0]);
		}

		private void processUnmached() {
			for (UnMach item : unMached) {
				DDLParser.ForeignContext context = item.context;
				Table orTable = item.orT;
				String reTableName = context.reference_definition().table_name().getText();
				Table reTable = schema.getTables().get(reTableName);
				if (reTable == null) {
					// still not found ... ignore
					continue;
				}
				Attribute[] or = getNames(context.parNameList().getText(), orTable);
				Attribute[] re = getNames(context.reference_definition().parNameList().getText(), reTable);
				for (int i = 0; i < or.length; i++) {
					if (or[i] == null || re[i] == null) {
						// sql typo???
						continue;
					}
//					System.out.println(orTable + "." + or[i] + "->" + reTable + "." + re[i] + "\n");
					orTable.getfKey().addReference(or[i], re[i]);
				}
			}
		}

		private Attribute[] getNames(String schema, Table table) {
			schema = schema.substring(1, schema.length()-1);
			String[] names = schema.split(",");
			Attribute[] result = new Attribute[names.length];
			for (int i = 0; i < names.length; i++) {
				result[i] = table.getAttrs().get(names[i]);
			}
			return result;
		}

		private boolean hasQuotes(String schema) {
			switch (schema.charAt(0)) {
				case '\'':
				case '\"':
				case '`':
					return true;
				default:
					return false;
			}
		}

		private String removeQuotes(String schema) {
			if (hasQuotes(schema)) {
				String result = null;
				result = schema.substring(1, schema.length()-1);
				return result;
			}
			return schema;
		}
	}
}

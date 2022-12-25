package AST;

import TYPES.*;
import SYMBOL_TABLE.*;
import java.io.PrintWriter;

public class AST_EXP_SUBSCRIPT extends AST_EXP
{
	/***************/
	/*  [var.]ID([exp[,exp]*]) */
	/***************/
	public AST_VAR var;
	public String fieldName;
	public AST_EXP_LIST expList;
	public PrintWriter fileWriter;
	public int lineNumber;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_EXP_SUBSCRIPT(AST_VAR var, String fieldName, AST_EXP_LIST expList, int lineNumber, PrintWriter fileWriter)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== stmt -> [var DOT]ID LPAREN [exp [exp]*] RPAREN\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
		this.fieldName = fieldName;
		this.expList = expList;
		this.lineNumber = lineNumber;
		this.fileWriter = fileWriter;
	}

	/*********************************************************/
	/* The printing message for an assign statement AST node */
	/*********************************************************/
	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST NODE SUBSCRIPT STMT\n");

		/***********************************/
		/* RECURSIVELY PRINT VAR + EXP ... */
		/***********************************/
		if (var != null) var.PrintMe();
		if (expList != null) expList.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"STMT\n[var.]ID([exp[,exp]*]);\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if(expList != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,expList.SerialNumber);
	}
	public TYPE SemantMe()
	{
		TYPE fieldType;
		if(var != null){
			TYPE varType = var.SemantMe();
			if (varType == null)
			{
				System.out.format(">> ERROR non existing variable with field %s\n",fieldName);
				fileWriter.write("ERROR(" + lineNumber + ")");
				fileWriter.close();
				System.exit(0);
			}
			if (!varType.isClass())
			{
				System.out.format(">> ERROR variable isn't of classType and has no field %s\n",fieldName);
				fileWriter.write("ERROR(" + lineNumber + ")");
				fileWriter.close();
				System.exit(0);
			}
			TYPE_CLASS varClass = (TYPE_CLASS)varType;
			fieldType = varClass.findMethodInClass(fieldName);
			if (fieldType==null){
				System.out.format(">> ERROR field %s isn't a method of the class\n",fieldName);
				fileWriter.write("ERROR(" + lineNumber + ")");
				fileWriter.close();
				System.exit(0);
			}
		}
		else{
			fieldType = SYMBOL_TABLE.getInstance().find(fieldName);
			if (fieldType == null)
			{
				System.out.format(">> ERROR %s doesn't exist in scope\n", fieldName);
				fileWriter.write("ERROR(" + lineNumber + ")");
				fileWriter.close();
				System.exit(0);
			}
			if(!(fieldType instanceof TYPE_FUNCTION)){
				System.out.format(">> ERROR %s can't be called as a function\n", fieldName);
				fileWriter.write("ERROR(" + lineNumber + ")");
				fileWriter.close();
				System.exit(0);
			}
		}
						
		TYPE_FUNCTION functionFieldType = (TYPE_FUNCTION)fieldType;
		TYPE_LIST expectedTypes = functionFieldType.params;
		TYPE_LIST argsTypes = null;
		if (expList != null){
			argsTypes = expList.SemantMe();
		}
		TYPE argType = null;
		TYPE expectedType = null;
		TYPE_CLASS tcarg = null;
		TYPE_CLASS tcexp = null;
		while(argsTypes != null && expectedTypes != null){
			argType = argsTypes.head;
			expectedType = expectedTypes.head;
			if (argType != expectedType){
				if (!((expectedType.isArray() || expectedType.isClass()) && argType == TYPE_NIL.getInstance())){
					if (expectedType.isClass() && argType.isClass()){
						tcarg = (TYPE_CLASS)argType;
						tcexp = (TYPE_CLASS)expectedType;
						if(tcarg.father != tcexp && tcarg.name != tcexp.name){
							System.out.format(">> ERROR %s called with unmatching argType %s\n", fieldName, expectedType.name);
							fileWriter.write("ERROR(" + lineNumber + ")");
							fileWriter.close();
							System.exit(0);
						}
					}
					else{
						System.out.format(">> ERROR %s called with unmatching argType %s\n", fieldName,expectedType.name);
						fileWriter.write("ERROR(" + lineNumber + ")");
						fileWriter.close();
						System.exit(0);
					}				
				}
			}						
			argsTypes = argsTypes.tail;
			expectedTypes = expectedTypes.tail;
		}
		if (argsTypes != null || expectedTypes != null){
			System.out.format(">> ERROR args number is not as expected in func %s\n", fieldName);
			fileWriter.write("ERROR(" + lineNumber + ")");
			fileWriter.close();
			System.exit(0);
		}		
		return functionFieldType.returnType;		
	}
}

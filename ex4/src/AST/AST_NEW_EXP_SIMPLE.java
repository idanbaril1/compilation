package AST;

import TYPES.*;
import SYMBOL_TABLE.*;
import TEMP.*;
import IR.*;
import MIPS.*;
import java.io.PrintWriter;

public class AST_NEW_EXP_SIMPLE extends AST_NEW_EXP
{
	/***************/
	/*  new type */
	/***************/
	public AST_TYPE type;
	public PrintWriter fileWriter;
	public int lineNumber;
	public TYPE_CLASS classType;
	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_NEW_EXP_SIMPLE(AST_TYPE type, int lineNumber, PrintWriter fileWriter)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== new type\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.type = type;
		this.lineNumber = lineNumber;
		this.fileWriter = fileWriter;
	}

	/*********************************************************/
	/* The printing message for an dec array AST node */
	/*********************************************************/
	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST NEW EXP SIMPLE STMT\n");

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"new type\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
	}
	public TYPE SemantMe()
	{
		TYPE t;
	
		/****************************/
		/* [1] Check If Type exists */
		/****************************/
		t = SYMBOL_TABLE.getInstance().find(type.type);
		if (t == null)
		{
			System.out.format(">> ERROR non existing type %s\n",type.type);
			fileWriter.write("ERROR(" + lineNumber + ")");
			fileWriter.close();
			System.exit(0);
		}	
		if(t == TYPE_INT.getInstance() || t == TYPE_STRING.getInstance()){
			System.out.format(">> ERROR new expression can't have primitive type %s\n",type.type);
			fileWriter.write("ERROR(" + lineNumber + ")");
			fileWriter.close();
			System.exit(0);
		}
		classType = (TYPE_CLASS)t;
		/*********************************************************/
		/* [4] Return value is t */
		/*********************************************************/
		return t;		
	}	
	public TEMP IRme()
	{
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		String className = type.type;
		int classSize = classType.classSize;
		int realClassSize = classType.realClassSize;
		TYPE_CLASS father = classType.father;
		IR.getInstance().Add_IRcommand(new IRcommand_NewClass(t, className, classSize, realClassSize, father));
		return t;
	}
}

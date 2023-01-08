package AST;

import TYPES.*;
import SYMBOL_TABLE.*;
import TEMP.*;
import IR.*;
import MIPS.*;
import java.io.PrintWriter;

public class AST_NEW_EXP_SUBSCRIPT extends AST_NEW_EXP 
{
	/***************/
	/*  new type[exp] */
	/***************/
	public AST_TYPE type;
	public AST_EXP exp;
	public PrintWriter fileWriter;
	public int lineNumber;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_NEW_EXP_SUBSCRIPT(AST_TYPE type, AST_EXP exp,int lineNumber, PrintWriter fileWriter)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== new type[exp]\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.type = type;
		this.exp = exp;
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
		System.out.print("AST NEW EXP SUBSCRIPT STMT\n");

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"new type[exp]\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
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
		if (exp.SemantMe() != TYPE_INT.getInstance()){
			System.out.format(">> ERROR array declared with non integral size\n");
			fileWriter.write("ERROR(" + lineNumber + ")");
			fileWriter.close();

			System.exit(0);
		}
		if ((exp instanceof AST_EXP_INT)){
			AST_EXP_INT intexp = (AST_EXP_INT)exp;
			if(intexp.value<=0){
				System.out.format(">> ERROR array declared with <=0 size\n");
				fileWriter.write("ERROR(" + lineNumber + ")");
				fileWriter.close();

				System.exit(0);
			}		
		}

		/*********************************************************/
		/* [4] Return value */
		/*********************************************************/
		return new TYPE_ARRAY(null, t);
	}	
	public TEMP IRme()
	{
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		TEMP size = exp.IRme();
		IR.getInstance().Add_IRcommand(new IRcommand_NewArray(t,size));
		return t;
	}
}

package AST;

import TYPES.*;
import SYMBOL_TABLE.*;
import TEMP.*;

public class AST_CFIELD_FUNC extends AST_CFIELD
{
	/***************/
	/*  funcDec */
	/***************/
	public AST_DEC_FUNC fd;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_CFIELD_FUNC(AST_DEC_FUNC fd)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== funcDec\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.fd = fd;
	}

	/*********************************************************/
	/* The printing message for an dec array AST node */
	/*********************************************************/
	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST CFIELD FUNC STMT\n");

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"FUNC DEC\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,fd.SerialNumber);
	}
	public TYPE SemantMe(TYPE_CLASS fatherClass)
	{
		TYPE t = null;
		if(fd != null){ 
			fd.SemantMe(fatherClass);
			t = SYMBOL_TABLE.getInstance().find(fd.name);
		}

		/*********************************************************/
		/* [4] Return value is t */
		/*********************************************************/
		return t;		
	}	
	public TEMP IRme(TYPE_CLASS classType)
	{
		if (fd != null){ 
			fd.name = classType.name + "_class_" + fd.name;
			return fd.IRme();
		}
		
		return null;
	}
}

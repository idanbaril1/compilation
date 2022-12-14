package AST;

import TYPES.*;

public class AST_ARG extends AST_EXP
{
	public AST_TYPE type;
	public String name;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_ARG(AST_TYPE type, String name)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== arg -> type name\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.type = type;
		this.name = name;
	}
	
	/***********************************************/
	/* The default message for an exp var AST node */
	/***********************************************/
	public void PrintMe()
	{
		/************************************/
		/* AST NODE TYPE = EXP VAR AST NODE */
		/************************************/
		System.out.print("AST NODE ARG\n");

		/*****************************/
		/* RECURSIVELY PRINT var ... */
		/*****************************/
		if (type != null) type.PrintMe();
		
		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"ARG\nTYPE ID");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
			
	}
	public TYPE SemantMe()
	{
		/*********************************************************/
		/* [4] Return value is irrelevant */
		/*********************************************************/
		return null;		
	}	
}

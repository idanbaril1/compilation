package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_NEW_EXP_SIMPLE extends AST_NEW_EXP
{
	/***************/
	/*  new type */
	/***************/
	public AST_TYPE type;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_NEW_EXP_SIMPLE(AST_TYPE type)
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
			System.exit(0);
		}	
		if(t == TYPE_INT.getInstance() || t == TYPE_STRING.getInstance()){
			System.out.format(">> ERROR new expression can't have primitive type %s\n",type.type);
			System.exit(0);
		}

		/*********************************************************/
		/* [4] Return value is t */
		/*********************************************************/
		return t;		
	}	
}

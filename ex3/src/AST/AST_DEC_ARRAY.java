package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_DEC_ARRAY extends AST_DEC_ABSTRACT
{
	/***************/
	/*  array ID = type[]; */
	/***************/
	public String name;
	public AST_TYPE type;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_DEC_ARRAY(String name, AST_TYPE type)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== arrayTypedef -> array ID = type[];\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.name = name;
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
		System.out.print("AST ARRAY DEC STMT\n");

		/***********************************/
		/* RECURSIVELY PRINT VAR + EXP ... */
		/***********************************/		

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"ARRAY DEC\narray ID = type[];\n");
		
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
			System.out.format(">> ERROR array def with non existing type %s\n",type.type);
			System.exit(0);
		}
		
		/**************************************/
		/* [2] Check That Name does NOT exist */
		/**************************************/
		if (SYMBOL_TABLE.getInstance().find(name) != null)
		{
			System.out.format(">> ERROR name %s already exists\n",name);				
		}
		
		/***************************************************/
		/* [3] Enter the Variable Type to the Symbol Table */
		/***************************************************/
		SYMBOL_TABLE.getInstance().enter(name, new TYPE_ARRAY(t));

		/*********************************************************/
		/* [4] Return value is irrelevant for var declarations */
		/*********************************************************/
		return null;		
	}
}

package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_DEC_CLASS extends AST_DEC_ABSTRACT
{
	/***************/
	/*  class ID [extends id] {cField [cField]*} */
	/***************/
	public String name;
	public String fatherName;
	public AST_CFIELD_LIST content;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_DEC_CLASS(String name, String fatherName, AST_CFIELD_LIST content)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== classDec -> class ID [extends id] {cField [cField]*}\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.name = name;
		this.fatherName = fatherName;
		this.content = content;
	}

	/*********************************************************/
	/* The printing message for an dec array AST node */
	/*********************************************************/
	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST CLASS DEC STMT\n");

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"CLASS DEC\nclass ID [extends id] {cField [cField]*}\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,content.SerialNumber);
	}
	public TYPE SemantMe()
	{	
		TYPE_CLASS fatherClass = null;
		if(fatherName != null){
			fatherClass = (TYPE_CLASS)SYMBOL_TABLE.getInstance().find(fatherName);
		}
		
		/*************************/
		/* [1] Begin Class Scope */
		/*************************/
		SYMBOL_TABLE.getInstance().beginScope();

		/***************************/
		/* [2] Semant Data Members */
		/***************************/
		TYPE_CLASS t = new TYPE_CLASS(fatherClass,name,(TYPE_LIST)content.SemantMe());

		/*****************/
		/* [3] End Scope */
		/*****************/
		SYMBOL_TABLE.getInstance().endScope();

		/************************************************/
		/* [4] Enter the Class Type to the Symbol Table */
		/************************************************/
		if (SYMBOL_TABLE.getInstance().find(name) != null)
		{
			System.out.format(">> ERROR class name %s already exists\n",name);				
		}
		SYMBOL_TABLE.getInstance().enter(name,t);

		/*********************************************************/
		/* [5] Return value is irrelevant for class declarations */
		/*********************************************************/
		return null;		
	}
}

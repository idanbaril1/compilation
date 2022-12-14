package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_DEC_FUNC extends AST_DEC_ABSTRACT
{
	/***************/
	/*  type ID([type ID [COMMA type ID]*]){stmt [stmt]*} */
	/***************/
	public String name;
	public AST_TYPE type;
	public AST_ARGS_LIST args;
	public AST_STMT_LIST content;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_DEC_FUNC(String name, AST_TYPE type, AST_ARGS_LIST args, AST_STMT_LIST content)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== funcDec -> type ID([type ID [COMMA type ID]*]){stmt [stmt]*}\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.name = name;
		this.type = type;
		this.args = args;
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
		System.out.print("AST FUNC DEC STMT\n");

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"FUNC DEC\ntype ID([type ID [COMMA type ID]*]){stmt [stmt]*}\n");
			
		type.PrintMe();
		if(args!=null) args.PrintMe();
		content.PrintMe();
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
		if(args!=null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,args.SerialNumber);
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,content.SerialNumber);
	}
	public TYPE SemantMe()
	{
		TYPE t;
		TYPE returnType = null;
		TYPE_LIST type_list = null;

		/*******************/
		/* [0] return type */
		/*******************/
		returnType = SYMBOL_TABLE.getInstance().find(type.type);
		if(type.type == "void"){
			returnType = TYPE_VOID.getInstance();
		}
		if (returnType == null)
		{
			System.out.format(">> ERROR non existing return type %s\n",returnType);				
		}
	
		/****************************/
		/* [1] Begin Function Scope */
		/****************************/
		SYMBOL_TABLE.getInstance().beginScope();
		/***************************/
		/* enter 'return' to scope table so return stmts will now the returnType */
		/* Since it's a saved word, IDs not allowed to have this name */
		/***************************/
		SYMBOL_TABLE.getInstance().enter("return", returnType);
		/***************************/
		/* [2] Semant Input Params */
		/***************************/
		for (AST_ARGS_LIST it = args; it != null; it = it.tail)
		{
			t = SYMBOL_TABLE.getInstance().find(it.head.type.type);
			if (t == null)
			{
				System.out.format(">> ERROR non existing type %s\n",it.head.type.type);				
			}
			else
			{
				type_list = new TYPE_LIST(t,type_list);
				SYMBOL_TABLE.getInstance().enter(it.head.name,t);
			}
		}

		/*******************/
		/* [3] Semant Body */
		/*******************/
		content.SemantMe();

		/*****************/
		/* [4] End Scope */
		/*****************/
		SYMBOL_TABLE.getInstance().endScope();

		/***************************************************/
		/* [5] Enter the Function Type to the Symbol Table */
		/***************************************************/
		SYMBOL_TABLE.getInstance().enter(name,new TYPE_FUNCTION(returnType,name,type_list));

		/*********************************************************/
		/* [6] Return value is irrelevant for class declarations */
		/*********************************************************/
		return null;		
	}
}

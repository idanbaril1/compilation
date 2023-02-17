package AST;

import TYPES.*;
import TEMP.*;

public class AST_ARGS_LIST extends AST_Node
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_ARG head;
	public AST_ARGS_LIST tail;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_ARGS_LIST(AST_ARG head,AST_ARGS_LIST tail)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (tail != null) System.out.print("====================== args -> arg args\n");
		if (tail == null) System.out.print("====================== args -> arg \n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.head = head;
		this.tail = tail;
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE ARGS LIST\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (head != null) head.PrintMe();
		if (tail != null) tail.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"ARGS\nLIST\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,head.SerialNumber);
		if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,tail.SerialNumber);
	}
	public TYPE_LIST SemantMe()
	{
		TYPE t1 = null;
		TYPE_LIST t2 = null;
		if(head!=null) {
			t1 = head.SemantMe();
		}
		if(tail!=null) {
			t2 = tail.SemantMe();
		}

		/*********************************************************/
		/* [4] Return value is list */
		/*********************************************************/
		return new TYPE_LIST(t1, t2);		
	}	
	
	
}

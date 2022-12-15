package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_STMT_SUBSCRIPT extends AST_STMT
{
	/***************/
	/*  [var.]ID([exp[,exp]*]); */
	/***************/
	public AST_VAR var;
	public String id;
	public AST_EXP_LIST expList;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_SUBSCRIPT(AST_VAR var, String id, AST_EXP_LIST expList)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== stmt -> [var DOT]ID LPAREN [exp [exp]*] RPAREN SEMICOLON\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
		this.id = id;
		this.expList = expList;
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
				System.out.format(">> ERROR non existing variable. Has no field %s\n",id);
				System.exit(0);
			}
			if (!varType.isClass())
			{
				System.out.format(">> ERROR variable isn't of classType and has no fields, including field %s\n",id);
				System.exit(0);
			}
		}
			
		// need to search in class scope if it's a method
		fieldType = SYMBOL_TABLE.getInstance().find(id);
		if (fieldType == null)
		{
			System.out.format(">> ERROR %s doesn't exist\n", id);
			System.exit(0);
		}
		if(!(fieldType instanceof TYPE_FUNCTION)){
			System.out.format(">> ERROR %s can't be called as a method\n", id);
			System.exit(0);
		}
		TYPE_FUNCTION functionFieldType = (TYPE_FUNCTION)fieldType;			
		return functionFieldType.returnType;		
	}
}

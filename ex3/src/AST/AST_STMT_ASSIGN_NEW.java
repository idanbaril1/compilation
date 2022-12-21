package AST;

import TYPES.*;

public class AST_STMT_ASSIGN_NEW extends AST_STMT
{
	/***************/
	/*  var := newExp; */
	/***************/
	public AST_VAR var;
	public AST_NEW_EXP ne;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_ASSIGN_NEW(AST_VAR var, AST_NEW_EXP ne)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== stmt -> var ASSIGN newExp SEMICOLON\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
		this.ne = ne;
	}

	/*********************************************************/
	/* The printing message for an assign statement AST node */
	/*********************************************************/
	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST NODE ASSIGN STMT\n");

		/***********************************/
		/* RECURSIVELY PRINT VAR + EXP ... */
		/***********************************/
		if (var != null) var.PrintMe();
		if (ne != null) ne.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"ASSIGN\nleft := right\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,ne.SerialNumber);
	}
	public TYPE SemantMe()
	{
		TYPE t1 = null;
		TYPE t2 = null;
		TYPE_CLASS tc1 = null;
		TYPE_CLASS tc2 = null;
		TYPE_ARRAY ta1 = null;
		TYPE_ARRAY ta2 = null;
		
		if (var != null) t1 = var.SemantMe();
		if (ne != null) t2 = ne.SemantMe();
		
		if((t1.isClass() || t1.isArray()) && t2 == TYPE_NIL.getInstance()){
			return null;
		}
		if(t1.isClass() && t2.isClass()){	
			tc1 = (TYPE_CLASS)t1;
			tc2 = (TYPE_CLASS)t2;
			if(tc2.father == tc1) return null;	
		}
		if(t1.isArray() && t2.isArray()){
			ta1 = (TYPE_ARRAY)t1;
			ta2 = (TYPE_ARRAY)t2;
			if(ta1.innerType == ta2.innerType && (ta1.name == ta2.name || ta2.name == null)) return null;
		}
		if (t1 != t2)
		{
			System.out.format(">> ERROR type mismatch for var := exp\n");	
			System.exit(0);
		}
		return null;
	}
}

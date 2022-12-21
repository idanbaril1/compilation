package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_DEC_VAR extends AST_DEC_ABSTRACT
{
	/***************/
	/*  type ID [= exp]; */
	/***************/
	public String name;
	public AST_TYPE type;
	public AST_EXP exp;
	public AST_NEW_EXP newExp;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_DEC_VAR(String name, AST_TYPE type, AST_EXP exp, AST_NEW_EXP newExp)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== type ID [= exp];\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.name = name;
		this.type = type;
		this.exp = exp;
		this.newExp = newExp;
	}

	/*********************************************************/
	/* The printing message for an dec array AST node */
	/*********************************************************/
	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST VAR DEC STMT\n");

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"VAR DEC\ntype ID [= exp/newExp];\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
		if(exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
		if(newExp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,newExp.SerialNumber);
	}
	public TYPE SemantMe(TYPE_CLASS fatherClass)
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
		
		/**************************************/
		/* [2] Check That Name does NOT exist in scope */
		/**************************************/
		if (SYMBOL_TABLE.getInstance().findInScope(name) != null)
		{
			System.out.format(">> ERROR variable %s already exists in scope\n",name);	
			System.exit(0);
		}
		if (fatherClass != null){
			TYPE fatherMember = fatherClass.findMethodInClass(name);
			if(fatherMember != null){
				System.out.format(">> ERROR can't override method %s with a field\n", name);
				System.exit(0);
				
			}	
			fatherMember = fatherClass.findFieldInClass(name);
			if (fatherMember!=null){
				if (fatherMember != t){
					System.out.format(">> ERROR can't override field %s with a different type %s\n", name, fatherMember.name);
					System.exit(0);
				}
			}
				
		}
		
		if(exp!=null || newExp!=null){
			TYPE expType = null;
			if (exp!=null){expType = exp.SemantMe();} 
			else {expType = newExp.SemantMe();}
			TYPE_CLASS tc1 = null;
			TYPE_CLASS tc2 = null;
			TYPE_ARRAY ta1 = null;
			TYPE_ARRAY ta2 = null;
			
			if((t.isClass() || t.isArray()) && expType == TYPE_NIL.getInstance()){
				SYMBOL_TABLE.getInstance().enter(name,t);
				return null;
			}
			if(t.isClass() && expType.isClass()){	
				tc1 = (TYPE_CLASS)t;
				tc2 = (TYPE_CLASS)expType;
				if(tc2.father == tc1){
					SYMBOL_TABLE.getInstance().enter(name,t);
					return null;
				}	
			}
			if(t.isArray() && expType.isArray()){
				ta1 = (TYPE_ARRAY)t;
				ta2 = (TYPE_ARRAY)expType;
				if(ta1.innerType == ta2.innerType && (ta1.name == ta2.name || ta2.name == null)){
					SYMBOL_TABLE.getInstance().enter(name,t);
					return null;
				} 
			}
			if (t != expType)
			{
				System.out.format(">> ERROR type mismatch for var := exp\n");	
				System.exit(0);
			}
		}
		
			
		/***************************************************/
		/* [3] Enter the Variable Type to the Symbol Table */
		/***************************************************/
		SYMBOL_TABLE.getInstance().enter(name,t);

		/*********************************************************/
		/* [4] Return value is irrelevant for var declarations */
		/*********************************************************/
		return null;		
	}
}

package AST;

import TYPES.*;
import TEMP.*;
import IR.*;
import java.io.PrintWriter;

public class AST_STMT_ASSIGN extends AST_STMT
{
	/***************/
	/*  var := exp */
	/***************/
	public AST_VAR var;
	public AST_EXP exp;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_ASSIGN(AST_VAR var,AST_EXP exp, int lineNumber, PrintWriter fileWriter)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== stmt -> var ASSIGN exp SEMICOLON\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
		this.exp = exp;
		this.lineNumber = lineNumber;
		this.fileWriter = fileWriter;
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
		if (exp != null) exp.PrintMe();

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
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
	}
	public TYPE SemantMe()
	{
		TYPE t1 = null;
		TYPE t2 = null;
		TYPE_CLASS tc1 = null;
		TYPE_CLASS tc2 = null;
		
		if (var != null) t1 = var.SemantMe();
		if (exp != null) t2 = exp.SemantMe();
		
		if((t1.isClass() || t1.isArray()) && t2 == TYPE_NIL.getInstance()){
			return null;
		}
		if(t1.isClass() && t2.isClass()){	
			tc1 = (TYPE_CLASS)t1;
			tc2 = (TYPE_CLASS)t2;
			if(tc1.name == tc2.name) return null;	
			while(tc2.father!=null){
				if(tc2.father.name == tc1.name) return null;
				tc2 = tc2.father;
			}
		}
		if (t1 != t2)
		{
			System.out.format(">> ERROR type mismatch for var := exp\n");
			fileWriter.write("ERROR(" + lineNumber + ")");
			fileWriter.close();			
			System.exit(0);
		}
		return null;
	}
	public TEMP IRme(String funcName)
	{
		TEMP src;
		if(var instanceof AST_VAR_SIMPLE){
			src = exp.IRme();
			IR.getInstance().Add_IRcommand(new IRcommand_Store(((AST_VAR_SIMPLE) var).name,src));
		}
		if(var instanceof AST_VAR_FIELD){
			TEMP obj = ((AST_VAR_FIELD) var).var.IRme();
			src = exp.IRme();
			String fieldName = ((AST_VAR_FIELD) var).fieldName;
			int fieldOffset = ((AST_VAR_FIELD) var).classType.getFieldOffset(fieldName);

			IR.getInstance().Add_IRcommand(new IRcommand_FieldSet(obj, fieldOffset, src));
		}
		if(var instanceof AST_VAR_SUBSCRIPT){
			TEMP arr = ((AST_VAR_SUBSCRIPT) var).var.IRme();
			TEMP index = ((AST_VAR_SUBSCRIPT) var).subscript.IRme();
			src = exp.IRme();
			IR.getInstance().Add_IRcommand(new IRcommand_ArraySet(arr, index , src));
		}
		
		

		return null;
	}
}

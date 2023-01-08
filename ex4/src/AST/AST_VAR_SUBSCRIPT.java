package AST;

import TYPES.*;
import java.io.PrintWriter;
import IR.*;
import TEMP.*;
import MIPS.*;

public class AST_VAR_SUBSCRIPT extends AST_VAR
{
	public AST_VAR var;
	public AST_EXP subscript;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_SUBSCRIPT(AST_VAR var,AST_EXP subscript,int lineNumber, PrintWriter fileWriter)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== var -> var [ exp ]\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
		this.subscript = subscript;
		this.lineNumber = lineNumber;
		this.fileWriter = fileWriter;
	}

	/*****************************************************/
	/* The printing message for a subscript var AST node */
	/*****************************************************/
	public void PrintMe()
	{
		/*************************************/
		/* AST NODE TYPE = AST SUBSCRIPT VAR */
		/*************************************/
		System.out.print("AST NODE SUBSCRIPT VAR\n");

		/****************************************/
		/* RECURSIVELY PRINT VAR + SUBSRIPT ... */
		/****************************************/
		if (var != null) var.PrintMe();
		if (subscript != null) subscript.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"SUBSCRIPT\nVAR\n...[...]");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var       != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if (subscript != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,subscript.SerialNumber);
	}
	public TYPE SemantMe()
	{
		TYPE varType = var.SemantMe();
		TYPE subType;
		
		if (varType == null)
		{
			System.out.format(">> ERROR non existing variable called as array\n");
			fileWriter.write("ERROR(" + lineNumber + ")");
			fileWriter.close();

			System.exit(0);
		}
		if (!varType.isArray()){
			System.out.format(">> ERROR variable that isn't array Can't be called with []\n");
			fileWriter.write("ERROR(" + lineNumber + ")");
			fileWriter.close();

			System.exit(0);
		}
		// need to search in class scope
		subType = subscript.SemantMe();
		if (subType != TYPE_INT.getInstance())
		{
			System.out.format(">> ERROR array must be called with integral index\n");
			fileWriter.write("ERROR(" + lineNumber + ")");
			fileWriter.close();

			System.exit(0);
		}
		TYPE_ARRAY arrVarType = (TYPE_ARRAY)varType;		
		return arrVarType.innerType;		
	}
	public TEMP IRme()
	{
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		TEMP t1 = var.IRme();
		TEMP t2 = subscript.IRme();
		IR.getInstance().Add_IRcommand(new IRcommand_ArrayAccess(t,t1,t2));
		return t;
	}
}

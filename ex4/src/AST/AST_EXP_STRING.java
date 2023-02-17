package AST;

import TYPES.*;
import TEMP.*;
import IR.*;

public class AST_EXP_STRING extends AST_EXP
{
	public String value;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_STRING(String value)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== exp -> STRING( %s )\n", value);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.value = value;
	}

	/************************************************/
	/* The printing message for an INT EXP AST node */
	/************************************************/
	public void PrintMe()
	{
		/*******************************/
		/* AST NODE TYPE = AST INT EXP */
		/*******************************/
		System.out.format("AST NODE STRING( %s )\n",value);

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("STRING(%s)",value));
	}
	public TYPE SemantMe()
	{
		return TYPE_STRING.getInstance();
	}
	public TEMP IRme()
	{	
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		String constName = IRcommand.getFreshLabel("string_const");
		IR.getInstance().Add_IRcommand(new IRcommandConstString(constName, value));
		IR.getInstance().Add_IRcommand(new IRcommand_Load_String(t, constName));
		return t;
	}
	public String getStringConst(){
		String name = IRcommand.getFreshLabel("str");
		IR.getInstance().Add_IRcommand(new IRcommandConstString(name,value));
		return name;
	}
}

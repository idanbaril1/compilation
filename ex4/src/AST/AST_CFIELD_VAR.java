package AST;

import TYPES.*;
import SYMBOL_TABLE.*;
import TEMP.*;
import IR.*;
import MIPS.*;
import java.io.PrintWriter;

public class AST_CFIELD_VAR extends AST_CFIELD
{
	/***************/
	/*  varDec */
	/***************/
	public AST_DEC_VAR vd;
	int intVal = 0;
	String strVal = null;
	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_CFIELD_VAR(AST_DEC_VAR vd)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== varDec\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.vd = vd;
	}

	/*********************************************************/
	/* The printing message for an dec array AST node */
	/*********************************************************/
	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST CFIELD VAR STMT\n");

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"VAR DEC\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,vd.SerialNumber);
	}
	public TYPE SemantMe(TYPE_CLASS fatherClass)
	{
		TYPE t = null;
		
		if(vd != null){ 
			vd.SemantMe(fatherClass);
			t = SYMBOL_TABLE.getInstance().find(vd.name);
		}
		if(t == TYPE_INT.getInstance() && vd.exp!=null){
			intVal = ((AST_EXP_INT)vd.exp).value;
		}
		if(t == TYPE_STRING.getInstance() && vd.exp!=null){
			strVal = ((AST_EXP_STRING)vd.exp).value;
		}
		/*********************************************************/
		/* [4] Return value is t */
		/*********************************************************/
		return new TYPE_CLASS_VAR_DEC(t, vd.name, intVal, strVal);	
	}	
	public TEMP IRme(TYPE_CLASS classType)
	{		
		if(intVal != 0|| strVal != null){
			int fieldOffset = classType.getFieldOffset(vd.name);
			IR.getInstance().Add_IRcommand(new IRcommand_Init_Field(fieldOffset, intVal, strVal));
		}
		
		return null;
	}
}

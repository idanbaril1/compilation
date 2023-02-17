package AST;

import TYPES.*;
import SYMBOL_TABLE.*;
import java.io.PrintWriter;
import IR.*;
import TEMP.*;
import MIPS.*;

public class AST_VAR_SIMPLE extends AST_VAR
{
	/************************/
	/* simple variable name */
	/************************/
	public String name;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_SIMPLE(String name,int lineNumber, PrintWriter fileWriter)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
	
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== var -> ID( %s )\n",name);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.name = name;
		this.lineNumber = lineNumber;
		this.fileWriter = fileWriter;
	}

	/**************************************************/
	/* The printing message for a simple var AST node */
	/**************************************************/
	public void PrintMe()
	{
		/**********************************/
		/* AST NODE TYPE = AST SIMPLE VAR */
		/**********************************/
		System.out.format("AST NODE SIMPLE VAR( %s )\n",name);

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("SIMPLE\nVAR\n(%s)",name));
	}
	public TYPE SemantMe()
	{
		TYPE t;
		
		t = SYMBOL_TABLE.getInstance().findInClassScope(name);
		if (t == null)
		{
			String className = SYMBOL_TABLE.getInstance().getClassScopeName();
			if (className!=null){
				TYPE_CLASS scopeClass = (TYPE_CLASS)SYMBOL_TABLE.getInstance().find(className);
				t = scopeClass.findFieldInClass(name);
			}
			if (t == null){			
				t = SYMBOL_TABLE.getInstance().find(name);				
			}			
			if (t == null)
			{
				System.out.format(">> ERROR %d %s doesn't exist\n", lineNumber, name);
				fileWriter.write("ERROR(" + lineNumber + ")");
				fileWriter.close();

				System.exit(0);
			}
		}
						
		return t;		
	}
	public TEMP IRme()
	{
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommand_Load(t,name));
		return t;
	}
}

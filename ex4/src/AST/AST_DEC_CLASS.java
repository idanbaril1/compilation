package AST;

import TYPES.*;
import SYMBOL_TABLE.*;
import TEMP.*;
import IR.*;
import MIPS.*;
import java.io.PrintWriter;

public class AST_DEC_CLASS extends AST_DEC_ABSTRACT
{
	/***************/
	/*  class ID [extends id] {cField [cField]*} */
	/***************/
	public String name;
	public String fatherName;
	public AST_CFIELD_LIST content;
	public PrintWriter fileWriter;
	public int lineNumber;
	public TYPE_CLASS classType;
	public TYPE_CLASS fatherClass;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_DEC_CLASS(String name, String fatherName, AST_CFIELD_LIST content, int lineNumber, PrintWriter fileWriter)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== classDec -> class ID [extends id] {cField [cField]*}\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.name = name;
		this.fatherName = fatherName;
		this.content = content;
		this.lineNumber = lineNumber;
		this.fileWriter = fileWriter;
	}

	/*********************************************************/
	/* The printing message for an dec array AST node */
	/*********************************************************/
	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST CLASS DEC STMT\n");

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"CLASS DEC\nclass ID [extends id] {cField [cField]*}\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,content.SerialNumber);
	}
	public TYPE SemantMe()
	{	
		if (!SYMBOL_TABLE.getInstance().isInGlobalScope()){
			System.out.format(">> ERROR class %s can only be defined in global scope\n",name);
			fileWriter.write("ERROR(" + lineNumber + ")");
			fileWriter.close();
			System.exit(0);
		}
		if(fatherName != null){
			fatherClass = (TYPE_CLASS)SYMBOL_TABLE.getInstance().find(fatherName);
			if(fatherClass == null){
				System.out.format(">> ERROR class %s doesn't exist\n",fatherName);
				fileWriter.write("ERROR(" + lineNumber + ")");
				fileWriter.close();
				System.exit(0);
			}
		}
		
		/*************************/
		/* [1] Begin Class Scope */
		/*************************/
		SYMBOL_TABLE.getInstance().beginScope(name);
		/* place holder for fields with type of the class */
		SYMBOL_TABLE.getInstance().enter(name,new TYPE_CLASS(fatherClass,name, null));
		/***************************/
		/* [2] Semant Data Members */
		/***************************/
		TYPE_CLASS t = new TYPE_CLASS(fatherClass,name,(TYPE_LIST)content.SemantMe(fatherClass));
		classType = t;
		/*****************/
		/* [3] End Scope */
		/*****************/
		SYMBOL_TABLE.getInstance().endScope();

		/************************************************/
		/* [4] Enter the Class Type to the Symbol Table */
		/************************************************/
		if (SYMBOL_TABLE.getInstance().find(name) != null)
		{
			System.out.format(">> ERROR class name %s already exists\n",name);
			fileWriter.write("ERROR(" + lineNumber + ")");
			fileWriter.close();
			System.exit(0);
		}
		SYMBOL_TABLE.getInstance().enter(name,t);

		/*********************************************************/
		/* [5] Return value is irrelevant for class declarations */
		/*********************************************************/
		return null;		
	}
	public TEMP IRme()
	{				
		String afterClass = "after_class_" + name;	
		IR.getInstance().Add_IRcommand(new IRcommand_Jump_Label(afterClass));
		IR.getInstance().Add_IRcommand(new IRcommand_Label(String.format("class_%s", name)));

		if (content != null) content.IRme(classType);
		IR.getInstance().Add_IRcommand(new IRcommand_Jump_Reg("$ra"));
		IR.getInstance().Add_IRcommand(new IRcommand_Label(afterClass));
		return null;
	}
}

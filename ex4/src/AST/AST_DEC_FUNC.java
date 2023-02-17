package AST;

import TYPES.*;
import SYMBOL_TABLE.*;
import TEMP.*;
import IR.*;
import MIPS.*;
import java.io.PrintWriter;

public class AST_DEC_FUNC extends AST_DEC_ABSTRACT
{
	/***************/
	/*  type ID([type ID [COMMA type ID]*]){stmt [stmt]*} */
	/***************/
	public String name;
	public AST_TYPE type;
	public AST_ARGS_LIST args;
	public AST_STMT_LIST content;
	public PrintWriter fileWriter;
	public int lineNumber;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_DEC_FUNC(String name, AST_TYPE type, AST_ARGS_LIST args, AST_STMT_LIST content, int lineNumber, PrintWriter fileWriter)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== funcDec -> type ID([type ID [COMMA type ID]*]){stmt [stmt]*}\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.name = name;
		this.type = type;
		this.args = args;
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
		System.out.print("AST FUNC DEC STMT\n");

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"FUNC DEC\ntype ID([type ID [COMMA type ID]*]){stmt [stmt]*}\n");
			
		type.PrintMe();
		if(args!=null) args.PrintMe();
		content.PrintMe();
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
		if(args!=null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,args.SerialNumber);
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,content.SerialNumber);
	}
	public TYPE SemantMe(TYPE_CLASS fatherClass)
	{
		TYPE t;
		TYPE returnType = null;
		TYPE_LIST type_list = null;

		/*******************/
		/* [0] return type */
		/*******************/
		returnType = SYMBOL_TABLE.getInstance().find(type.type);
		if(type.type == "void"){
			returnType = TYPE_VOID.getInstance();
		}
		if (returnType == null)
		{
			System.out.format(">> ERROR non existing return type %s\n",type.type);	
			fileWriter.write("ERROR(" + lineNumber + ")");
			fileWriter.close();
			System.exit(0);			
		}
		/**************************************/
		/* Check That Name does NOT exist in scope */
		/**************************************/
		if (SYMBOL_TABLE.getInstance().findInScope(name) != null)
		{
			System.out.format(">> ERROR function %s already exists in scope\n",name);	
			fileWriter.write("ERROR(" + lineNumber + ")");
			fileWriter.close();
			System.exit(0);
		}
		if (fatherClass != null){
			TYPE fatherMember = fatherClass.findFieldInClass(name);
			if (fatherMember!=null){
				System.out.format(">> ERROR can't override field %s with method\n",name);
				fileWriter.write("ERROR(" + lineNumber + ")");
				fileWriter.close();				
				System.exit(0);
			}
			fatherMember = fatherClass.findMethodInClass(name);
			if(fatherMember != null){
				TYPE_FUNCTION fatherMethod = (TYPE_FUNCTION)fatherMember;
				if(fatherMethod.returnType != returnType){					
					System.out.format(">> ERROR can't override function %s with different return type\n",name);	
					fileWriter.write("ERROR(" + lineNumber + ")");
					fileWriter.close();
					System.exit(0);
				}
				TYPE_LIST expectedTypes = fatherMethod.params;
				TYPE_LIST argsTypes = null;
				if(args!=null){
					argsTypes = args.SemantMe();
				}					 
				TYPE argType = null;
				TYPE expectedType = null;
				TYPE_CLASS tcarg = null;
				TYPE_CLASS tcexp = null;
				while(argsTypes != null && expectedTypes != null){
					argType = argsTypes.head;
					expectedType = expectedTypes.head;
					if (argType != expectedType){
						System.out.format(">> ERROR can't overload method %s with different arg types\n",name);
						fileWriter.write("ERROR(" + lineNumber + ")");
						fileWriter.close();
						System.exit(0);
					}						
					argsTypes = argsTypes.tail;
					expectedTypes = expectedTypes.tail;
				}
				if (argsTypes != null || expectedTypes != null){
					System.out.format(">> ERROR can't overload method %s with different amount of args %s\n", name);
					fileWriter.write("ERROR(" + lineNumber + ")");
					fileWriter.close();
					System.exit(0);
				}	
			}
		}
		
		/****************************/
		/* [1] Begin Function Scope */
		/****************************/
		SYMBOL_TABLE.getInstance().beginScope();
		/***************************/
		/* enter 'return' to scope table so return stmts will know the returnType */
		/* Since it's a saved word, IDs not allowed to have this name */
		/***************************/
		SYMBOL_TABLE.getInstance().enter("return", returnType);
		/***************************/
		/* [2] Semant Input Params */
		/***************************/
		if (args != null){
			type_list = args.SemantMe();
		}
		/* for recursive calls */
		SYMBOL_TABLE.getInstance().enter(name,new TYPE_FUNCTION(returnType,name,type_list));
		/*******************/
		/* [3] Semant Body */
		/*******************/
		content.SemantMe();

		/*****************/
		/* [4] End Scope */
		/*****************/
		SYMBOL_TABLE.getInstance().endScope();

		/***************************************************/
		/* [5] Enter the Function Type to the Symbol Table */
		/***************************************************/
		SYMBOL_TABLE.getInstance().enter(name,new TYPE_FUNCTION(returnType,name,type_list));

		/*********************************************************/
		/* [6] Return value is irrelevant for class declarations */
		/*********************************************************/
		return null;		
	}
	public TEMP IRme()
	{
		if(name.equals("main")){
			IR.getInstance().Add_IRcommand(new IRcommand_Label(name));
			if (content != null) content.IRme(name);
			return null;
		}
		String afterFunc = "after_" + name;	
		IR.getInstance().Add_IRcommand(new IRcommand_Jump_Label(afterFunc));				
		IR.getInstance().Add_IRcommand(new IRcommand_Label(name));
		IR.getInstance().Add_IRcommand(new IRcommand_Prologue(name, args));
		if (content != null) content.IRme(name);
		IR.getInstance().Add_IRcommand(new IRcommand_Epilogue(name, args));
		IR.getInstance().Add_IRcommand(new IRcommand_Label(afterFunc));
		return null;
	}
}

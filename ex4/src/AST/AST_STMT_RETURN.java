package AST;

import TYPES.*;
import SYMBOL_TABLE.*;
import TEMP.*;
import IR.*;
import java.io.PrintWriter;

public class AST_STMT_RETURN extends AST_STMT
{
	/***************/
	/* return [exp]; */
	/***************/
	public AST_EXP exp;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_RETURN(AST_EXP exp, int lineNumber, PrintWriter fileWriter)
	{
		this.exp = exp;
		this.lineNumber = lineNumber;
		this.fileWriter = fileWriter;
		
		SerialNumber = AST_Node_Serial_Number.getFresh();
	}
	public void PrintMe()
	{
		
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST RETURN STMT\n");

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"return exp\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if(exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
	}
	public TYPE SemantMe()
	{	TYPE t = null;
		if(exp != null) t = exp.SemantMe();		
		TYPE expectedReturnType = SYMBOL_TABLE.getInstance().find("return");
		if (expectedReturnType == null){
			System.out.format(">> ERROR no returnType in function scope");
			fileWriter.write("ERROR(" + lineNumber + ")");
			fileWriter.close();			
			System.exit(0);
		}
		
		if (expectedReturnType==TYPE_VOID.getInstance()){
			if( t != null){
				System.out.format(">> ERROR function is of type void and return stmt isn't null");
				fileWriter.write("ERROR(" + lineNumber + ")");
				fileWriter.close();			
				System.exit(0);
			}
			return null;
		}
		
		if(expectedReturnType != t){
			System.out.format(">> ERROR expectedReturnType is %s and return stmt's type is %s", expectedReturnType, t);	
			fileWriter.write("ERROR(" + lineNumber + ")");
			fileWriter.close();
			System.exit(0);
		}
		
		return null;		
	}
	public TEMP IRme(String funcName)
	{
		TEMP t = null;
		if(exp != null){
			t = exp.IRme();
		}
		IR.getInstance().Add_IRcommand(new IRcommand_Return(t, funcName));

		return null;
	}
}
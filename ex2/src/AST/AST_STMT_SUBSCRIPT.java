package AST;

public class AST_STMT_SUBSCRIPT extends AST_STMT
{
	/***************/
	/*  [var.]ID([exp[,exp]*]); */
	/***************/
	public AST_VAR var;
	public String id;
	public AST_EXP[] expArr;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_SUBSCRIPT(AST_VAR var, String id, AST_EXP[] expArr)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== stmt -> [var DOT]ID LPAREN [exp [exp]*] RPAREN SEMICOLON\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
		this.id = id;
		this.expArr = expArr;
	}

	/*********************************************************/
	/* The printing message for an assign statement AST node */
	/*********************************************************/
	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST NODE SUBSCRIPT STMT\n");

		/***********************************/
		/* RECURSIVELY PRINT VAR + EXP ... */
		/***********************************/
		if (var != null) var.PrintMe();
		if (expArr[0] != null) expArr[0].PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"STMT\n[var.]ID([exp[,exp]*]);\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if(expArr != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,expArr[0].SerialNumber);
	}
}

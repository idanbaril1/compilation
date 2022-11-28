package AST;

public class AST_DEC_CLASS extends AST_DEC_ABSTRACT
{
	/***************/
	/*  class ID [extends id] {cField [cField]*} */
	/***************/
	public String name;
	public String fatherName;
	public AST_CFIELD[] content;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_DEC_CLASS(String name, String fatherName, AST_CFIELD[] content)
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
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,content[0].SerialNumber);
	}
}

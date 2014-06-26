package util;

import java.text.DecimalFormat;
/**
 * 根据传入数据，生成系统报销单据号码
 * 暂且不考虑对单据号进行记录，不进行查询唯一处理、
 * 
 * @author veny
 * 2014-6-25 19:41:13
 */
public class ReceiptNumGenerator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println(ReceiptNumGenerator.getInstance().getBankCardNum("1"));
		//System.out.println(ReceiptNumGenerator.getInstance().getExpenseNum("13214.98"));
		System.out.println(ReceiptNumGenerator.getInstance().getReceiptNum("XYH","01","Y10","userid","C","13214.98"));
		
	}
	private static class ReceiptNumGeneratorHolder  {
		public final static ReceiptNumGenerator receiptNumGen = new ReceiptNumGenerator();
	}
	
	public static  ReceiptNumGenerator getInstance(){
		return ReceiptNumGeneratorHolder.receiptNumGen;
	}
	
	/**
	 * 根据参数获取单据号码
	 * @param projCode  项目代码
	 * @param happenDay 费用发生时间 （两位字符 如 01）
	 * @param expenseType 费用类别
	 * @param userId  报销人ID
	 * @param payType 支付方式
	 * @param expense 费用金额，保留小数点后两位
	 * @return  ReceiptNum 单据号
	 */
	public String getReceiptNum (String projCode,String happenDay,String expenseType, String userId,String payType,String expense ){
		StringBuffer receiptBuffer = new StringBuffer();
		//根据userid获取银行卡号。获取方式暂未实现
		String cardNum = this.getBankCardNum(userId);
		String expenseNum = this.getExpenseNum(expense);
		receiptBuffer.append(projCode).append(happenDay).append(expenseType).append(cardNum).append(payType).append(expenseNum);
		return receiptBuffer.toString();
	}

	/**
	 * 对金额进行处理
	 * @param expense 费用金额，保留两位小数格式
	 * @return  转换后的费用金额
	 */
	private String getExpenseNum(String expense) {
		String expenseNum = expense.replace(".","");
		DecimalFormat df = new DecimalFormat("000000000"); 
		expenseNum = df.format(Integer.parseInt(expenseNum));
		return expenseNum;
	}

	/**
	 * 根据用户ID获取用户登记的银行卡号码。
	 * @param userId 用户唯一标识
	 * @return bakNum 银行卡后四位字符
	 */
	private String getBankCardNum(String userId) {
		// 根据userId获取登记的银行卡号
		String bankNum = "6225885321242266";
		return bankNum.substring(bankNum.length()-4);
	}

}

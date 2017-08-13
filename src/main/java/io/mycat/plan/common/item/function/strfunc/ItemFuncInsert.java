package io.mycat.plan.common.item.function.strfunc;

import java.util.List;

import io.mycat.plan.common.item.Item;
import io.mycat.plan.common.item.function.ItemFunc;


public class ItemFuncInsert extends ItemStrFunc {

	public ItemFuncInsert(List<Item> args) {
		super(args);
	}

	@Override
	public final String funcName() {
		return "insert";
	}

	@Override
	public String valStr() {
		String orgStr = args.get(0).valStr();
		long pos = args.get(1).valInt().longValue();
		long len = args.get(2).valInt().longValue();
		String newStr = args.get(3).valStr();
		if (args.get(0).isNull() || args.get(1).isNull() || args.get(2).isNull() || args.get(3).isNull()) {
			this.nullValue = true;
			return EMPTY;
		}
		long orgLen = orgStr.length();
		if (pos <= 0 || pos > orgLen)
			return orgStr;
		StringBuilder sb = new StringBuilder(orgStr);
		if (len < 0 || pos + len > orgLen)
			return sb.replace((int) pos - 1, (int) orgLen - 1, newStr).toString();
		else
			return sb.replace((int) (pos - 1), (int) (pos - 1 + len), newStr).toString();
	}
	@Override
	public ItemFunc nativeConstruct(List<Item> realArgs) {
		return new ItemFuncInsert(realArgs);
	}
}

package Runtime;

import java.util.Map;
import java.util.HashMap;

@SuppressWarnings("all")
public class Variables {
    private Map<String, Object> AValues;
    private Map<String, Object> AConst, BConst;

	public Variables(Map<String, Object> BConst) {
		AValues = new HashMap<String, Object>();
		AConst = new HashMap<String, Object>();
	}

	private Object getMyValue(String id) {
		Object value = AConst.get(id);
		if(value == null) {
			value = AValues.get(id);
		}
		return value;
	}

    public boolean contain(String id){
		if(AValues.containsKey(id)) {
			return true;
		} else {
			return AConst.containsKey(id);
		}
    }

    public Object getValue(String id) {
		if(id.charAt(0) == 'B' && id.charAt(1) == '[') {
			return BConst.get(id);
		} else {
			return getMyValue(id);
		}
    }

    public void setValue(String id, Object value){
        AValues.put(id, value);
    }

    public void clear(){
        AValues.clear();
		AConst.clear();
    }

	public static void main(String[] args) {
	}
}

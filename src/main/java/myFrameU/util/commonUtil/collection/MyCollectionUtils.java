package myFrameU.util.commonUtil.collection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public final class MyCollectionUtils {

	public static void main(String args[]) {
	}

	/**
	 * 打印输出list,遍历,作为测试用的.必须使用泛型
	 */
	public static void printlnList(List<? extends Object> list,
			List<String> jilianMethods) throws Exception {
		//
		if (null != list) {
			System.out.println("list集合里大小为:" + list.size());
			Class c = null;
			String inner_name = null;
			String inner_simapleName = null;
			Method[] m = null;
			Object[] os = {};
			Class<?> returnClass = null;
			String returnTypeName = null;
			for (Object o : list) {
				System.out.println("=============" + o + "=================");
				c = o.getClass();
				// entity.news.NewsType
				inner_name = c.getName();
				// NewsType
				inner_simapleName = c.getSimpleName();
				if (inner_simapleName.equals("Integer")
						|| inner_simapleName.equals("Float")
						|| inner_simapleName.equals("String")
						|| inner_simapleName.equals("Date")) {
					System.out.println(o.toString());
				} else {
					// 如果是自定义的
					m = c.getDeclaredMethods();
					for (int i = 0; i < m.length; i++) {
						if (m[i].getName().startsWith("get")) {
							returnClass = m[i].getReturnType();
							returnTypeName = returnClass.getSimpleName();
							if (returnTypeName.equals("int")
									|| returnTypeName.equals("Integer")
									|| returnTypeName.equals("float")
									|| returnTypeName.equals("Float")
									|| returnTypeName.equals("String")
									|| returnTypeName.equals("Date")) {
								System.out.println(m[i].getName() + "="
										+ m[i].invoke(o, os));
							} else if (returnTypeName.equals("Set")) {
								// 如果是Set集合
								if (null != jilianMethods) {
									if (jilianMethods.contains(m[i].getName())) {
										// 如果需要输出这个级联Set集合对象的数据
										List list1 = (List) m[i].invoke(o, os);
										System.out.println(m[i].getName()
												+ ".size()=" + list1.size());
									}
								}
							} else if (returnTypeName.equals("List")) {
								// 如果是List集合
							} else if (returnTypeName.equals("Map")) {
								// 如果是Map
							} else {
								// 如果是自定义的
							}
							// System.out.println(m[i].getReturnType().getSimpleName());

						}
					}
				}
			}
		} else {
			System.out.println("list为空");
		}

	}

	// 返回list集合里的第一个元素
	public static Object get0(List list) {
		if (null != list) {
			if (list.size() > 0) {
				return list.get(0);
			}
		} else {
			return null;
		}
		return list;
	}

	/**
	 * 找出一个装有自定义List的所有的id，然后拼出用来查询in（ids）的ids
	 * 
	 * @param list
	 * @param c
	 * @return
	 */
	public static String createHQLin_ids(List<?> list, Class c) {
		StringBuffer sb = new StringBuffer();
		String s = null;
		try {
			Class[] cs = {};
			Method m = c.getDeclaredMethod("getId", cs);//
			Object[] os = {};

			Integer id = null;
			for (Object o : list) {
				id = (Integer) (m.invoke(o, os));
				sb.append(id).append(",");
			}
			s = sb.toString();
			if (!s.equals("")) {
				s = s.substring(0, s.length() - 1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	// 将list分组，组成map，map的key为分组的id
	public Map<Integer, List<?>> groupList(List<?> list,
			Map<Integer, List<?>> map, String questionMark, String mapKeyMethod) {
		try {
			Class c = Class.forName(questionMark);
			Class[] cs = {};
			Method m = c.getDeclaredMethod(mapKeyMethod, cs);//
			Object[] os = {};

			// 第一步：先建立map的key
			Integer id = null;
			for (Object o : list) {
				id = (Integer) (m.invoke(o, os));
				if (map.containsKey(id)) {

				} else {
					map.put(id, new ArrayList());
				}
			}

			// 第二步：再次循环list，根据map的key填充相应的ni
			for (Object o : list) {
				id = (Integer) (m.invoke(o, os));
				if (map.containsKey(id)) {
					List oldList = map.get(id);
					oldList.add(o);
				} else {
				}
			}

			// 第三步：循环map结果
			for (Entry<Integer, List<?>> entry : map.entrySet()) {
				System.out.print(entry.getKey() + ":" + "\t");
				List<?> niList = entry.getValue();
				for (Object o : niList) {
					System.out.println(o.getClass());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	// 去除数组中重复的记录
	public static String[] array_unique(String[] a) {
		// array_unique
		List<String> list = new LinkedList<String>();
		for (int i = 0; i < a.length; i++) {
			if (!list.contains(a[i])) {
				list.add(a[i]);
			}
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	// ====================================================
	public static boolean numberArrayEqual(String[] array1, String[] array2) {
		Arrays.sort(array1);
		Arrays.sort(array2);
		if (Arrays.equals(array1, array2)) {
			return true;
			// System.out.println("两个数组中的元素值相同");
		} else {
			return false;
			// System.out.println("两个数组中的元素值不相同");
		}
	}

	// 将【1,4,3,0,6,2】转换为【0,1,2,3,4,6】
	public static String sortStringArray(String s) {
		if (null == s || s.equals("")) {
			return "";
		}

		String[] s_array = null;
		if (s.contains(",")) {
			s_array = s.split(",");
		} else {
			s_array = new String[1];
			s_array[0] = s;
		}
		int len = s_array.length;

		int[] int_array = new int[len];
		for (int i = 0; i < len; i++) {
			int_array[i] = new Integer(s_array[i]).intValue();
		}
		Arrays.sort(int_array);

		StringBuffer sb = new StringBuffer("");
		int size = int_array.length;
		for (int i = 0; i < size; i++) {
			if (i == (size - 1)) {
				sb.append(int_array[i]);
			} else {
				sb.append(int_array[i]).append(",");
			}
		}
		return sb.toString();
	}

	// ========================================
	// 将map转为list，因为cache中存放的是map
	public static <T> List<T> map2list(HashMap<String, T> map) {
		List<T> list = null;
		if(null!=map){
			list = new ArrayList<T>();
			for (Map.Entry<String,T> entry : map.entrySet()) {
				list.add(entry.getValue());
			}
		}
		
		return list;
	}
	public static <T> List<T> map2list(TreeMap<String, T> map) {
		List<T> list = null;
		if(null!=map){
			list = new ArrayList<T>();
			for (Map.Entry<String,T> entry : map.entrySet()) {
				list.add(entry.getValue());
			}
		}
		
		return list;
	}
}

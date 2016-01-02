package test;

import java.util.SortedMap;
import java.util.TreeMap;

import myFrameU.util.commonUtil.twoDimensional.QRCodeEventsZXING;
import myFrameU.util.commonUtil.twoDimensional.TwoDimensionalCodeSwetakeQRCodeUtil;
import myFrameU.util.httpUtil.path.PathUtil;
import myFrameU.weixin.init.InitConfig;
import myFrameU.weixin.pay.entity.WxPayBackEntity;
import myFrameU.weixin.pay.util.ReceiveXmlProcess;
import myFrameU.weixin.pay.util.WXPayUtil;

public class Test6 {

	public static void main(String[] args) {
		String imagePathName = "/img/user/admin2weima1.png";
		String path = "E:/合伙人/application/hehuoren/WebContent";
		
		QRCodeEventsZXING.createImg(path+imagePathName,
				"http://weixin.shanxity.com/admin/adminWxId", 400);
		
		
		
	}

}

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="productTypeListBox">
				<div class="alertSonBox" id="alertSonBox">
					<#list productTypeList as pt>
						<#if pt.isROOT==0>
							<div class="alertSonBox_every" id="alertSonBox_every_dataIndex_${pt_index}">
						         	<div class="alertSonBox_every_l">
						         		<#list pt.childs as pt2>
											<a href="<%=request.getContextPath()%>/fgauction/qpt-[${pt2.id}]-expand-o-pager1.htm"><span>${pt2.name}</span></a>
										</#list>
						         	</div>
						         	
						    </div>
						</#if>
					</#list>
			    </div>
				<div id="productTypeBox">
					<ul class="cate-con">
						<#list productTypeList as pt>
							<#if pt.isROOT==0>
								<li>
						            <a class="cate J_Cate" style="height: 32px" href="<%=request.getContextPath()%>/fgchannel/pt${pt.id}.htm" target="_blank" data-index="${pt_index}">
						                <span class="iconfont-nav ${pt.fm}"></span><span class="name">${pt.name}</span>
						                <span class="arrow"></span>
						                <i class="space"></i>
						            </a>
						        </li>
							</#if>
						</#list>
	    			</ul>
				</div>
			</div>
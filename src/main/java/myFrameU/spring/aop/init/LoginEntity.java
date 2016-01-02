package myFrameU.spring.aop.init;

public class LoginEntity {

	//角色class
	private String roleClass;
	//角色前缀
	private String prefix;
	//放在session里角色的key
	private String saveRoleSessionKey;
	//如果没有登录，则跳转到的路径
	private String ifNotLoginPath;
	
	public String getRoleClass() {
		return roleClass;
	}
	public void setRoleClass(String roleClass) {
		this.roleClass = roleClass;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getSaveRoleSessionKey() {
		return saveRoleSessionKey;
	}
	public void setSaveRoleSessionKey(String saveRoleSessionKey) {
		this.saveRoleSessionKey = saveRoleSessionKey;
	}
	public String getIfNotLoginPath() {
		return ifNotLoginPath;
	}
	public void setIfNotLoginPath(String ifNotLoginPath) {
		this.ifNotLoginPath = ifNotLoginPath;
	}
	
	
}

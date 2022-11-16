package Action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import Bean.Contacts;
import Bean.Kind;
import Bean.Page;
import Service.ContactsService;

@Controller
public class CantactsAction {
	ContactsService contactsService;
	Page page;
	
	public ContactsService getContactsService() {
		return contactsService;
	}
	@Resource
	public void setContactsService(ContactsService contactsService) {
		this.contactsService = contactsService;
	}
	//��ѯ������
	@RequestMapping("findkind.do")
	public String findkind(HttpSession session,HttpServletRequest request){
		String pageNum=request.getParameter("p")==null?"1":request.getParameter("p");
	    request.setAttribute("page", contactsService.getKindPage(Integer.valueOf(pageNum)));
	    request.setAttribute("y", 1);
		return "show";
	}
	//��ѯ������ϵ��
	@RequestMapping("findAll.do")
	public String getContactsPage(HttpSession session,HttpServletRequest request){
		String pageNum=request.getParameter("p")==null?"1":request.getParameter("p");
	    request.setAttribute("page", contactsService.getContactsPage(Integer.valueOf(pageNum)));
		return "allShow";
	}
	//��ѯ������ϵ��
	@RequestMapping("findcontacts.do")
	public String findcontacts(HttpSession session,HttpServletRequest request,Contacts contacts){
		String PersonName=contacts.getPersonName();
		int kindId=Integer.parseInt(request.getParameter("kindId").toString());
		request.setAttribute("kindid",kindId);
		String pageNum=request.getParameter("p")==null?"1":request.getParameter("p");
	    request.setAttribute("page", contactsService.getContactsOfSome(Integer.valueOf(pageNum),PersonName));
		return "contactsShow";
	}
	//��������ϵ���в�ѯ
	@RequestMapping("findcontactss.do")
	public String findcontactss(HttpSession session,HttpServletRequest request,Contacts contacts){
		String PersonName=contacts.getPersonName();
		String pageNum=request.getParameter("p")==null?"1":request.getParameter("p");
	    request.setAttribute("page", contactsService.getContactsOfSome(Integer.valueOf(pageNum),PersonName));
		return "allShow";
	}
	//�������в�ѯ��
	@RequestMapping("findSomeKind.do")
	public String findSomeKind(HttpSession session,HttpServletRequest request,Kind kind){
		String kindName=kind.getKindName();
		String pageNum=request.getParameter("p")==null?"1":request.getParameter("p");
	    request.setAttribute("page", contactsService.getSomeKind(Integer.valueOf(pageNum),kindName));
		return "show";
	}
	//�����½���ϵ��,�û�������Ҫ������
	@RequestMapping("registerContacts.do")
	public String registerContacts(@Valid@ModelAttribute("command") Contacts contacts,BindingResult bindingResult,HttpSession session,HttpServletRequest request){
		if(bindingResult.hasErrors()){
			request.setAttribute("id",contacts.getKindId());
			return "registerContacts";
		}
		int n=contactsService.registerContacts(contacts,contacts.getKindId());
		if(n>0){
			String pageNum=request.getParameter("p")==null?"1":request.getParameter("p");
		    request.setAttribute("page", contactsService.getContactsPageOfSome(Integer.valueOf(pageNum),contacts.getKindId()));
		    request.setAttribute("kindid",contacts.getKindId());
			return "contactsShow";
		}else{
			return "registerContactsFail";
		}
	}
	//�½���ϵ��
	@RequestMapping("registerContactss.do")
	public String registerContactss(@Valid@ModelAttribute("command") Contacts contacts,BindingResult bindingResult,HttpSession session,HttpServletRequest request){
		if(bindingResult.hasErrors()){
			request.setAttribute("list",contactsService.findkind());
			return "registerContacts1";
		}
		int n=contactsService.registerContacts(contacts);
		if(n>0){
			String pageNum=request.getParameter("p")==null?"1":request.getParameter("p");
		    request.setAttribute("page", contactsService.getContactsPage(Integer.valueOf(pageNum)));
			return "allShow";
		}else{
			return "registerContactsFail";
		}
	}
	//�½���
	@RequestMapping("registerKind.do")
	public String registerKind(@Valid@ModelAttribute("command") Kind kind,BindingResult bindingResult,HttpSession session,HttpServletRequest request){
		if(bindingResult.hasErrors()){
			return "registerKind";
		}
		int n=contactsService.registerKind(kind);
		if(n>0){
			String pageNum=request.getParameter("p")==null?"1":request.getParameter("p");
		    request.setAttribute("page", contactsService.getKindPage(Integer.valueOf(pageNum)));
			return "show";
		}else{
			return "registerKindFail";
		}
	}
	//��ȡ��,�½���ϵ��
	@RequestMapping("registerContacts1.do")
	public String findkind(HttpServletRequest request,HttpSession session){
		request.setAttribute("list",contactsService.findkind());
		return "registerContacts1";
	}
	//����ʾ���������ϵ��
	@RequestMapping("updateContacts.do")
	public String updateContacts(@Valid@ModelAttribute("command") Contacts contacts,BindingResult bindingResult,HttpSession session,HttpServletRequest request){
	    int n=contactsService.updateContacts(contacts);
	    if(n>0){
	    	int id=Integer.parseInt(request.getParameter("kindId").toString());
	    	String pageNum=request.getParameter("p")==null?"1":request.getParameter("p");
		    request.setAttribute("page", contactsService.getContactsPageOfSome(Integer.valueOf(pageNum),id));
		    request.setAttribute("kindid",id);
			return "contactsShow";
	    }
		return "updateContactsFail";
	}
	//ȫ����ϵ����ʾ���������ϵ��
	@RequestMapping("updateContactss.do")
	public String updateContactss(@Valid@ModelAttribute("command") Contacts contacts,BindingResult bindingResult,HttpSession session,HttpServletRequest request){
	    int n=contactsService.updateContacts(contacts);
	    if(n>0){
	    	String pageNum=request.getParameter("p")==null?"1":request.getParameter("p");
		    request.setAttribute("page", contactsService.getContactsPage(Integer.valueOf(pageNum)));
			return "allShow";
	    }
		return "updateContactsFail";
	}
	//�����ɾ����ϵ��
	@RequestMapping("deleteContacts.do")
	public String deleteContacts(Contacts contacts,HttpSession session,HttpServletRequest request){
		int id=Integer.parseInt(request.getParameter("id").toString());
		int kindId=Integer.parseInt(request.getParameter("kindId").toString());
		boolean n=contactsService.deleteContacts(id);
		if(n){
			String pageNum=request.getParameter("p")==null?"1":request.getParameter("p");
		    request.setAttribute("page", contactsService.getContactsPageOfSome(Integer.valueOf(pageNum),kindId));
		    request.setAttribute("kindid",kindId);
			return "contactsShow";
		}else{			
			return "fail";
		}
	}
	//ȫ����ϵ�˽���ɾ����ϵ��
	@RequestMapping("deleteContactss.do")
	public String deleteContactss(Contacts contacts,HttpSession session,HttpServletRequest request){
		int id=Integer.parseInt(request.getParameter("id").toString());
		boolean n=contactsService.deleteContacts(id);
		if(n){
			String pageNum=request.getParameter("p")==null?"1":request.getParameter("p");
		    request.setAttribute("page", contactsService.getContactsPage(Integer.valueOf(pageNum)));
			return "allShow";
		}else{			
			return "fail";
		}
	}
	//ɾ����
	@RequestMapping("deleteKind.do")
	public String deleteKind(Kind kind,HttpSession session,HttpServletRequest request){
		int id=Integer.parseInt(request.getParameter("id"));
		boolean n=contactsService.deleteKind(id);
		if(n){
			String pageNum=request.getParameter("p")==null?"1":request.getParameter("p");
			request.setAttribute("page", contactsService.getKindPage(Integer.valueOf(pageNum)));
			return "show";
		}else{
			return "fail";
		}	
	}
	//��ѯ�����µ���ϵ��
	@RequestMapping("queryById.do")
	public String queryById(HttpSession session,HttpServletRequest request){
	    int id=Integer.parseInt(request.getParameter("id"));
	    String pageNum=request.getParameter("p")==null?"1":request.getParameter("p");
	    request.setAttribute("page", contactsService.getContactsPageOfSome(Integer.valueOf(pageNum),id));
	    request.setAttribute("kindid",id);
	    return "contactsShow";
	}
	//����Id��ѯ��ϵ����Ϣ��ת�޸�ҳ
	@RequestMapping("queryContactsById.do")
	public String queryContactsById(HttpSession session,HttpServletRequest request){
	    int id=Integer.parseInt(request.getParameter("id"));
	    List list=contactsService.queryContactsById(id);
	    request.setAttribute("listt",contactsService.findkind());
	    session.setAttribute("list", list);
	    return "updateContacts";
	}
	//����Id��ѯ��ϵ����Ϣ��ת�޸�ҳ
	@RequestMapping("queryContactsByIdd.do")
	public String queryContactsByIdd(HttpSession session,HttpServletRequest request){
	    int id=Integer.parseInt(request.getParameter("id"));
	    List list=contactsService.queryContactsById(id);
	    request.setAttribute("listt",contactsService.findkind());
	    session.setAttribute("list", list);
	    return "updateContactss";
	}
	//����ʾ�ķ�ҳ
	 @RequestMapping("list.do")
	    public String list(HttpServletRequest request,Contacts contacts,HttpSession session) {
				String pageNum=request.getParameter("p")==null?"1":request.getParameter("p");
				request.setAttribute("page",contactsService.getKindPage(Integer.valueOf(pageNum)));
	        	return "show";
	 }
	 //ȫ����ϵ����ʾ�ķ�ҳ
	 @RequestMapping("listt.do")
	    public String listt(HttpServletRequest request,Contacts contacts,HttpSession session) {
			int kindId=Integer.parseInt(request.getParameter("kindId").toString());
				String pageNum=request.getParameter("p")==null?"1":request.getParameter("p");
				request.setAttribute("page",contactsService.getContactsPageOfSome(Integer.valueOf(pageNum),kindId));
				request.setAttribute("kindid",kindId);
	        	return "contactsShow";
	 }
	 //������ϵ����ʾ�ķ�ҳ
	 @RequestMapping("listtt.do")
	    public String listtt(HttpServletRequest request,Contacts contacts,HttpSession session) {
				String pageNum=request.getParameter("p")==null?"1":request.getParameter("p");
				request.setAttribute("page",contactsService.getContactsPage(Integer.valueOf(pageNum)));
	        	return "allShow";
	 }
	
}

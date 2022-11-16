package Service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;


import Bean.Contacts;
import Bean.Kind;
import Bean.Page;
import Dao.ContactsDao;

@Component
public class ContactsService {
	ContactsDao contactsDao;

	public ContactsDao getContactsDao() {
		return contactsDao;
	}
	@Resource
	public void setContactsDao(ContactsDao contactsDao) {
		this.contactsDao = contactsDao;
	}
	public int registerContacts(Contacts contacts,int id){
		return contactsDao.registerContacts(contacts,id);
	}
	public int registerKind(Kind kind){
		return contactsDao.registerKind(kind);
	}
	public List findKind(){
		return contactsDao.findKind();
	}	
	public List findContacts(){
		return contactsDao.findContacts();
	}
	public Page getContactsPageOfSome(int pageNum,int id){
		 return contactsDao.getContactsPageOfSome(pageNum,id);
	 }
	public Page getContactsPage(int pageNum){
		 return contactsDao.getContactsPage(pageNum);
	 }
	public Page getKindPage(int pageNum){
		 return contactsDao.getKindPage(pageNum);
	 }
	public  int updateContacts(Contacts contacts) {
		return contactsDao.updateContacts(contacts);
	}
	public boolean deleteContacts(int id) {
		return contactsDao.deleteContacts(id);
	}
	public List queryById(int id) {
		return contactsDao.queryById(id);
	}
	public boolean deleteKind(int id) {
		return contactsDao.deleteKind(id);
	}
	public List queryContactsById(int id) {
		return contactsDao.queryContactsById(id);
	}
	public Page getContactsOfSome(int pageNum,String PersonName) {
		return contactsDao.getContactsOfSome(pageNum, PersonName);
	}
	public Page getSomeKind(int pageNum, String kindName) {
		return contactsDao.getSomeKind(pageNum,kindName);
	}
	public int registerContacts(Contacts contacts) {
		return contactsDao.registerContacts(contacts);
	}
	public List findkind() {
		return contactsDao.findkind();
	}
}

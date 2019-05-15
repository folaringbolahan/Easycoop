package com.sift.easycoopfin.models;

import org.orm.PersistentException;

public class MemberProcessor {

    private int id;
    private int companyId;
    private String memberNo;
    private String action = "";

    public void setId(int value) {
        this.id = value;
    }

    public int getId() {
        return id;
    }

    public void setMemberNo(String value) {
        this.memberNo = value;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setCompanyId(int value) {
        this.companyId = value;
    }

    public int getCompanyId() {
        return companyId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String process() {
        String result = "Unexcepted result";
        if (action.equals("search")) {
            try {
                com.sift.easycoopfin.models.Member _member = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().loadMemberByORMID(getId());
                if (_member != null) {
                    copyFromBean(_member);
                    result = "Search success";
                } else {
                    result = "Search failed";
                }
            } catch (PersistentException e) {
                result = "Search error: " + e.toString();
            }
        } else if (action.equals("insert")) {
            try {
                com.sift.easycoopfin.models.Member _member = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().createMember();
                copyToBean(_member);
                if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().save(_member)) {
                    result = "Insert success";
                } else {
                    result = "Insert failed";
                }
            } catch (Exception e) {
                result = "Insert error: " + e.toString();
            }
        } else if (action.equals("update")) {
            try {
                com.sift.easycoopfin.models.Member _member = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().loadMemberByORMID(getId());
                if (_member != null) {
                    copyToBean(_member);
                    if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().save(_member)) {
                        result = "Update success";
                    } else {
                        result = "Update failed";
                    }
                } else {
                    result = "Update failed";
                }

            } catch (PersistentException e) {
                result = "Update error: " + e.toString();
            }
        } else if (action.equals("delete")) {
            try {
                com.sift.easycoopfin.models.Member _member = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().loadMemberByORMID(getId());
                if (_member != null && com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().delete(_member)) {
                    result = "Delete success";
                } else {
                    result = "Delete failed";
                }
            } catch (PersistentException e) {
                result = "Delete error: " + e.toString();
            }
        } else if (action.equals("")) {
            result = "";
        }
        action = "";
        return result;
    }

    private void copyFromBean(com.sift.easycoopfin.models.Member _member) {
        setCompanyId(_member.getCompanyId());
        setId(_member.getORMID());
    }

    private void copyToBean(com.sift.easycoopfin.models.Member _member) {
        _member.setCompanyId(getCompanyId());
    }
}

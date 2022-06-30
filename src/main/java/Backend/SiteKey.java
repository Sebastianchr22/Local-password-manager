/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

/**
 *
 * @author sebastian
 */
public class SiteKey {
    private String username;
    private String password;
    private String site;

    protected SiteKey(String site, String username, String password) {
        this.username = username;
        this.password = password;
        this.site = site;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSite() {
        return site;
    }

    protected void setUsername(String username) {
        this.username = username;
    }

    protected void setPassword(String password) {
        this.password = password;
    }

    protected void setSite(String site) {
        this.site = site;
    }
    
    
}

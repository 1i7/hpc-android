package clock.server;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.FromContainsFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;

//public class Jabber implements Runnable {
public class Jabber {
  private String LOGIN;
  private String PASSWORD;
  public static final String SERVER = "hpc.sadko.mobi";
  public static final String ADMIN = "admin@hpc.sadko.mobi";

  private XMPPConnection CONNECTION;
  private Roster ROSTER;

  public static Jabber CLIENT;

  public Jabber(String login, String password) {
    this.LOGIN = login;
    this.PASSWORD = password;
  }

  public static boolean registerUser(String login, String password)

  {
    try {
      XMPPConnection RegisterConnection = new XMPPConnection(SERVER);
      RegisterConnection.connect();
      AccountManager RegisterAccountManager = new AccountManager(RegisterConnection);
      // RegisterConnection
      // .getAccountManager();
      RegisterAccountManager.createAccount(login, password);
      RegisterConnection.disconnect();
      return true;
    } catch (Exception ex) {
      return false;
    }
  }

  public boolean run() {
    CONNECTION = new XMPPConnection(SERVER);
    try {
      int priority = 10;
      CONNECTION.connect();
      CONNECTION.login(LOGIN, PASSWORD);

      Presence presence = new Presence(Presence.Type.available);
      presence.setStatus("<coords>");
      CONNECTION.sendPacket(presence);
      presence.setPriority(priority);

      ROSTER = CONNECTION.getRoster();

      Collection<RosterEntry> entries = ROSTER.getEntries();
      String rosterstring = new String("");
      for (RosterEntry entry : entries) {
        rosterstring += entry.toString() + "\n";
      }
      sendMessage(ADMIN, "***Roster***\n" + rosterstring + "***END***\n");

      // sendMessage(ADMIN, ROSTER.getPresence("admin@hpc.sadko.mobi")
      // .toString());

      PacketFilter filter = new AndFilter(new PacketTypeFilter(Message.class));
      PacketListener myListener = new PacketListener() {
        public void processPacket(Packet packet) {
          if (packet instanceof Message) {
            Message message = (Message) packet;
            // обработка входящего сообщения
            processMessage(message);
          }
        }
      };

      CONNECTION.addPacketListener(myListener, filter);

      return true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return false;
    }
  }

  private void processMessage(Message message) {
    // String messageBody = message.getBody();
    if (message.getBody().compareTo("0") != 0) {
      sendMessage(message.getFrom(), "ACCESS DENIED");
      return;
    }
    String JID = message.getFrom();
    String messageBody = ROSTER.getPresence(JID).toString() + ":\n" + message.getBody() + "\n";

    sendMessage(JID, messageBody);
  }

  public void sendMessageForAll(String message) {
    Collection<RosterEntry> entries = ROSTER.getEntries();
    for (RosterEntry entry : entries) {
      sendMessage(entry.getUser(), message);
    }
  }

  public String getStatusFromAll() {
    String result = new String("result:\n");
    Collection<RosterEntry> entries = ROSTER.getEntries();
    for (RosterEntry entry : entries) {
      // sendMessage(entry.getUser(), message);
      result += entry.getUser() + ": " + ROSTER.getPresence(entry.getUser()).getStatus() + "\n";
    }
    return result;
  }

  public void setStatus(String status) {
    Presence presence = new Presence(Presence.Type.available);
    presence.setStatus(status);
    CONNECTION.sendPacket(presence);
    presence.setPriority(10);
  }

  public void sendMessage(String to, String message) {
    if (!message.equals("")) {
      ChatManager chatmanager = CONNECTION.getChatManager();
      Chat newChat = chatmanager.createChat(to, null);

      try {
        newChat.sendMessage(message);
      } catch (XMPPException e) {
        System.out.println(e.getMessage());
      }
    }
  }
}

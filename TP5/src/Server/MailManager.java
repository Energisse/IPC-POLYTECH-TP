package Server;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;

public class MailManager {
    /**
     * The deleted mails
     */
    private final HashSet<Integer> deletedMails = new HashSet<>();

    /**
     * The sub server
     */
    private final SousServeur ss;

    /**
     * Create a new mail manager
     *
     * @param ss the sub server
     */
    public MailManager(SousServeur ss) {
        this.ss = ss;
    }

    /**
     * Get the mails
     * @return the mails
     */
    public Mail[] getMails() {
        File[] files = this.getMailsFiles();

        ArrayList<Mail> mails = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            if (getDeletedMails().contains(i + 1)) continue;
            mails.add(new Mail(i, files[i]));
        }

        return mails.toArray(new Mail[0]);
    }

    /**
     * Get a mail
     * @param index the index of the mail
     * @return the mail
     */
    public @Nullable Mail getMail(int index) {
        File[] files = this.getMailsFiles();

        if ( index < 0 || index >= files.length) {
            return null;
        }

        Arrays.sort(files, Comparator.comparingLong(File::lastModified));
        for (int i = 0; i < files.length; i++) {
            if (i == index) {
                return new Mail(i, files[i]);
            }
        }

        return null;
    }

    /**
     * Get the mails files
     * @return the mails files
     */
    private File[] getMailsFiles() {
        File file = new File(".\\mails\\" + ss.getUser());
        File[] files = file.listFiles();

        if (files != null) {
            Arrays.sort(files, Comparator.comparingLong(File::lastModified));
        }
        else{
            files = new File[0];
        }

        return files;
    }

    /**
     * Delete a mail
     * @param index the index of the mail
     */
    public void deleteMail(int index) {
        deletedMails.add(index);
    }

    /**
     * Check if a mail is deleted
     * @param index the index of the mail
     * @return if the mail is deleted
     */
    public boolean isDeletedMail(int index) {
        return deletedMails.contains(index);
    }

    /**
     * Reset the deleted mails
     */
    public void resetDeletedMails() {
        deletedMails.clear();
    }

    /**
     * Get the deleted mails
     * @return the deleted mails
     */
    public HashSet<Integer> getDeletedMails() {
        return deletedMails;
    }

    /**
     * Delete the mails
     */
    public void deleteMails() {
        try {
            File[] files = this.getMailsFiles();

            for (int i = 0; i < files.length; i++) {
                if(isDeletedMail(i+1))files[i].delete();
            }

            this.resetDeletedMails();
        }
        catch (Exception ignored) {
        }
    }
}

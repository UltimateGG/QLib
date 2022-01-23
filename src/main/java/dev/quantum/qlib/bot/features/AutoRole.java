package dev.quantum.qlib.bot.features;

import dev.quantum.qlib.bot.QBot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Automatically give a user a role (or multiple) when they join the server */
public class AutoRole extends Feature {
    /** The role(s) to give to the user */
    private ArrayList<String> rolesToAdd = new ArrayList<>();

    /**
     * Constructor
     * @param rolesToAdd The role(s) to give to the user
     */
    public AutoRole(String... rolesToAdd) {
        this.rolesToAdd.addAll(Arrays.asList(rolesToAdd));
    }

    @SubscribeEvent
    public void onMemberJoin(GuildMemberJoinEvent e) {
        for (String role : rolesToAdd) {
            Role r = getRole(e.getGuild(), role);
            if (r != null) e.getGuild().addRoleToMember(e.getMember(), r).queue();
            else QBot.LOGGER.warn("[AutoRole] Could not find role: " + role);
        }
    }

    private Role getRole(Guild g, String role) {
        if (role == null) return null;

        // Try by name first
        List<Role> roles = g.getRolesByName(role, true);
        if (roles.size() > 1) QBot.LOGGER.warn("[AutoRole] Found multiple roles with name: " + role);
        if (roles.size() > 0) return roles.get(0);

        try { // Try by ID
            return g.getRoleById(role);
        } catch (Exception ignored) {}

        return null;
    }

    /**
     * Get the roles to give to the user
     * @return The roles to give to the user
     */
    public ArrayList<String> getRolesToAdd() {
        return rolesToAdd;
    }

    /**
     * Set the roles to give to the user
     * @param rolesToAdd The roles to give to the user
     */
    public void setRolesToAdd(ArrayList<String> rolesToAdd) {
        this.rolesToAdd = rolesToAdd;
    }

    /**
     * Add a role to give to the user
     * @param roleToAdd The role to give to the user
     */
    public void addRoleToAdd(String roleToAdd) {
        this.rolesToAdd.add(roleToAdd);
    }

    /**
     * Remove a role to give to the user
     * @param roleToRemove The role to remove from the user
     */
    public void removeRoleToAdd(String roleToRemove) {
        this.rolesToAdd.remove(roleToRemove);
    }
}

package ru.brfg.fgfamily.utlis;

import org.bukkit.ChatColor;
import ru.brfg.fgfamily.utlis.BConfig;

public enum Lang {
    PREFIX("&f[&cСвадьбы&f] "),
    MSG_SECONDS("секунд"),
    MSG_MINUTES("минут"),
    MSG_HOURS("часов"),
    MSG_DAYS("дней"),
    MSG_WEEKS("недель"),
    MSG_MONTH("месяца"),
    MSG_YEARS("лет"),
    MSG_NOACCESS("У вас нет прав для использования этой команды"),
    MSG_PLAYERONLY("Только игрок может использовать эту команду"),
    MSG_INMARRYONLY("Эту команду возможно использовать находясь в браке"),
    MSG_NOMARRYONLY("Эту команду невозможно использовать находясь в браке"),
    MSG_USAGE("Неверный аргумент. Использование: %usage%"),
    MSG_HELP("Помощь"),
    MSG_INVALIDPLAYER("Игрок не найден"),
    MSG_INVITE_ALREADYMARRIED("Игрок уже в браке"),
    MSG_INVITE_ALREADYINVITED("Данный игрок уже получил приглашение сыграть свадьбу, попробуйте позже"),
    MSG_INVITE_INVITED_INVITED("Вы пригласили %player% сыграть свадьбу"),
    MSG_INVITE_INVITED_BY("Игрок %player% пригласил вас сыграть свадьбу"),
    MSG_ACCEPT_NOTINVITED("У вас нет приглашений"),
    MSG_ACCEPT_ACCEPTED("Игрок %player% женился на %player1%"),
    MSG_DECLINE_NOTINVITED("У вас нет приглашений"),
    MSG_INVITE_ALREADYSEND("Вы уже отправили приглашение сыграть свадьбу"),
    MSG_INVITE_OWNSELF("Вы не можете сыграть свадьбу с самим собой"),
    MSG_DECLINE_DECLINED_DECLINED("Вы отменили приглашение %player% сыграть свадьбу"),
    MSG_DECLINE_DECLINED_BY("%player% отменил ваше приглашение, %money%$ возвращены на баланс"),
    MSG_INVITE_NOMONEY("Чтобы сыграть свадьбу необходимо иметь %money%$ на счету"),
    MSG_DIVORCE_NOMONEY("Необходимо иметь %money%$ чтобы развестись"),
    MSG_DIVORCE_DIVORCED("%player% и %player1% расторгнули брак"),
    MSG_TELEPORT_OFFLINE("Ваш партнер оффлайн"),
    MSG_TELEPORT_TELEPORTED_TELEPORTED("Вы телепортировались к %player%"),
    MSG_TELEPORT_TELEPORTED_BY("Ваш партнер %player% телепортировался к вам"),
    MSG_SEEN_ONLINE("Ваш партнер онлайн"),
    MSG_SEEN_OFFLINE("Ваш партнер оффлайн %time%"),
    MSG_INFO_INFO("Игрок: %player%\nСтатус: %status\nПартнер: %partner%\n"),
    MSG_INFO_STATUS_MARRIED("В браке"),
    MSG_INFO_STATUS_NOTMARRIED("Холост"),
    MSG_INFO_NOPARTNER("Отсутствует"),
    MSG_BALANCE_INFO("Баланс: %money%"),
    MSG_BALANCE_ADD_ADDED("Вы добавили %money% в бюджет семьи"),
    MSG_BALANCE_TAKE_TAKED("Вы взяли %money% из бюджета семьи"),
    MSG_BALANCE_ADD_NOMONEY("У вас недостаточно денег"),
    MSG_BALANCE_TAKE_NOMONEY("В бюджете семьи недостаточно денег"),
    MSG_LIST_EMPTY("Список браков пуст"),
    MSG_LIST_INFO("%ind%. %player% и %player1% в браке %time%"),
    MSG_PVP_ENABLE("Вы включили пвп с партнером"),
    MSG_PVP_DISABLE("Вы выключили пвп с партнером"),
    MSG_INVITE_NOTIME("Игрок не успел ответить на ваше приглашение"),
    MSG_LIST_PAGE("[Страница %page%/%count%]"),
    MSG_KISS("Игрок %player% поцеловал %player1%");

    private String defaultValue;
    private static BConfig config;

    private Lang(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getPath() {
        return this.name().replace("_", ".");
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public String toString() {
        return this.fixColors(config.getConfig().getString(this.getPath()));
    }

    public String toMsg() {
        return this.fixColors(config.getConfig().getString(PREFIX.getPath()) + config.getConfig().getString(this.getPath()));
    }

    public static void setConfig(BConfig config) {
        Lang.config = config;
        Lang.load();
    }

    private static void load() {
        for (Lang lang : Lang.values()) {
            if (config.getConfig().getString(lang.getPath()) != null) continue;
            config.getConfig().set(lang.getPath(), (Object)lang.getDefaultValue());
        }
        config.save();
    }

    private String fixColors(String s) {
        if (s == null) {
            return "";
        }
        return ChatColor.translateAlternateColorCodes((char)'&', (String)s);
    }
}


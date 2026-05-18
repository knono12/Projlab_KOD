package view.popups;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import java.util.Map;
import java.util.HashMap;

import model.players.Player;
import model.vehicles.Vehicle;
import model.accessories.fuels.BioKerosene;
import model.accessories.fuels.Salt;
import model.players.BusDriver;
import model.players.Cleaner;
import model.vehicles.Snowplow;
import model.vehicles.Bus;
import model.accessories.fuels.Gravel;
import model.accessories.attachments.*;

/**
 * ShopFrame with sample items for quick UI testing.
 */
public class ShopFrame extends JFrame {

    private static final String CATEGORY_FUEL = "Fuel";
    private static final String CATEGORY_ATTACHMENT = "Attachment";
    private static final String CATEGORY_VEHICLES = "Vehicles";
    private static final String[] MAIN_CATEGORIES = { CATEGORY_FUEL, CATEGORY_ATTACHMENT, CATEGORY_VEHICLES };

    private JList<String> itemList;
    private JList<String> selectableList;
    private JButton buyButton;
    private JButton useButton;
    private JButton closeButton;

    private Player player;
    private Vehicle vehicle;

    public ShopFrame(Cleaner c, Snowplow s) {
        super("Shop");

        this.player = c;
        this.vehicle = s;

        initializeCleanerList();
        addCleanerActionListeners();
        initialize();
    }

    public ShopFrame(BusDriver b, Bus bus) {
        super("Shop");

        this.player = b;
        this.vehicle = bus;

        initializeBusdriverList();
        addBusdriverActionListeners();
        initialize();
    }

    private void initialize() {

        setLocationRelativeTo(null);

        buyButton = new JButton("Buy");
        useButton = new JButton("Use");
        closeButton = new JButton("Close");

        JPanel center = new JPanel(new BorderLayout(8, 8));
        JScrollPane leftScroll = new JScrollPane(itemList);
        leftScroll.setPreferredSize(new Dimension(260, 200));
        JScrollPane rightScroll = new JScrollPane(selectableList);
        rightScroll.setPreferredSize(new Dimension(160, 200));

        center.add(leftScroll, BorderLayout.CENTER);
        center.add(rightScroll, BorderLayout.EAST);

        JPanel bottom = new JPanel();
        bottom.add(buyButton);
        bottom.add(useButton);
        bottom.add(closeButton);

        this.setLayout(new BorderLayout());
        this.add(center, BorderLayout.CENTER);
        this.add(bottom, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
    }

    private void addCleanerActionListeners() {
        buyButton.addActionListener(snowplowBuyAction());
        useButton.addActionListener(snowplowUseAction());
        closeButton.addActionListener(e -> dispose());

    }

    private ActionListener snowplowBuyAction() {
        return e -> {
            if (CATEGORY_VEHICLES.equals(itemList.getSelectedValue())
                    || "Snowplow".equals(selectableList.getSelectedValue())) {
                Cleaner cleaner = (Cleaner) player;
                Snowplow sp = (Snowplow) vehicle;
                cleaner.addSnowplow(sp);
            } else if (CATEGORY_FUEL.equals(itemList.getSelectedValue())) {
                // TODO: specify metrics for fuels
                switch (selectableList.getSelectedValue()) {
                    case "Salt":
                        player.addToInventory(new Salt(getName(), 10, 0));
                        break;
                    case "Biokerosene":
                        player.addToInventory(new BioKerosene(getName(), 10, 0));
                        break;
                    case "Gravel":
                        player.addToInventory(new Gravel(getName(), 10, 0));
                        break;
                    default:
                        break;
                }
            } else {
                // TODO: specify price for attachments
                switch (selectableList.getSelectedValue()) {
                    case "Sweeper":
                        player.addToInventory(new SweeperAttachment(getName(), 0));
                        break;
                    case "Gritter":
                        player.addToInventory(new GritterAttachment(getName(), 0));
                        break;
                    case "Ice Breaker":
                        player.addToInventory(new IceBreakerAttachment(getName(), 0));
                        break;
                    case "Snow Blade":
                        player.addToInventory(new SnowBladeAttachment(getName(), 0));
                        break;
                    case "Flame Thrower":
                        player.addToInventory(new FlamethrowerAttachment(getName(), 0));
                        break;
                    case "Graveler":
                        player.addToInventory(new GravelAttachment(getName(), 0));
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private ActionListener snowplowUseAction() {
        Snowplow sp = (Snowplow) vehicle;
        return e -> {
            switch (selectableList.getSelectedValue()) {
                case "Sweeper":
                    sp.changeAttachment(new SweeperAttachment(getName(), 0));
                    break;
                case "Gritter":
                    sp.changeAttachment(new GritterAttachment(getName(), 0));
                    break;
                case "Ice Breaker":
                    sp.changeAttachment(new IceBreakerAttachment(getName(), 0));
                    break;
                case "Snow Blade":
                    sp.changeAttachment(new SnowBladeAttachment(getName(), 0));
                    break;
                case "Flame Thrower":
                    sp.changeAttachment(new FlamethrowerAttachment(getName(), 0));
                    break;
                case "Graveler":
                    sp.changeAttachment(new GravelAttachment(getName(), 0));
                    break;
                default:
                    break;
            }
        };
    }

    private void addBusdriverActionListeners() {
        buyButton.addActionListener(busBuyAction());

        useButton.addActionListener(e -> {
        });
        closeButton.addActionListener(e -> dispose());
    }

    private ActionListener busBuyAction() {
        return e -> {
            if (CATEGORY_VEHICLES.equals(itemList.getSelectedValue())
                    || "Bus".equals(selectableList.getSelectedValue())) {
                BusDriver busDriver = (BusDriver) player;
                Bus bus = (Bus) vehicle;
                busDriver.addBus(bus);
            }
        };
    }

    void initializeCleanerList() {
        itemList = new JList<>(MAIN_CATEGORIES);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        Map<String, String[]> optionsMap = new HashMap<>();
        optionsMap.put(CATEGORY_FUEL, new String[] { "Salt", "Biokerosene", "Gravel" });
        optionsMap.put(CATEGORY_ATTACHMENT,
                new String[] { "Sweeper", "Gritter", "Ice Breaker", "Snow Blade", "Flame Thrower", "Graveler" });
        optionsMap.put(CATEGORY_VEHICLES, new String[] { "Snowplow" });

        initList(optionsMap);
    }

    void initializeBusdriverList() {
        itemList = new JList<>(MAIN_CATEGORIES);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        Map<String, String[]> optionsMap = new HashMap<>();
        optionsMap.put(CATEGORY_VEHICLES, new String[] { "Bus" });

        initList(optionsMap);

    }

    void initList(Map<String, String[]> optionsMap) {
        selectableList = new JList<>(new String[0]);
        selectableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        itemList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selected = itemList.getSelectedValue();
                    String[] opts = optionsMap.get(selected);
                    if (opts != null) {
                        selectableList.setListData(opts);
                        if (opts.length > 0)
                            selectableList.setSelectedIndex(0);
                    } else {
                        selectableList.setListData(new String[0]);
                    }
                }
            }
        });
    }

    // public void onInventoryChanged() {
    // }
}

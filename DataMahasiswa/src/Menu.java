import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Menu extends JFrame{
    public static void main(String[] args) {
        Menu window = new Menu();
        window.setSize(720, 560);
        window.setLocationRelativeTo(null);
        window.setContentPane(window.mainPanel);
        window.getContentPane().setBackground(Color.white);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private int selectedIndex = -1;
    private ArrayList<Mahasiswa> listMahasiswa;

    private JPanel mainPanel;
    private JTable mahasiswaTable;
    private JLabel titleLabel;
    private JTextField nimField;
    private JTextField namaField;
    private JComboBox jenisKelaminComboBox;
    private JLabel nimLabel;
    private JLabel namaLabel;
    private JLabel jenisKelaminLabel;
    private JLabel tanggalLahirLabel;
    private JComboBox tanggalComboBox;
    private JComboBox bulanComboBox;
    private JComboBox tahunComboBox;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JButton deleteButton;

    private void resize() {
        mahasiswaTable.getColumnModel().getColumn(0).setPreferredWidth(25);
        mahasiswaTable.getColumnModel().getColumn(1).setPreferredWidth(60);
        mahasiswaTable.getColumnModel().getColumn(2).setPreferredWidth(160);
        mahasiswaTable.getColumnModel().getColumn(3).setPreferredWidth(85);
        mahasiswaTable.getColumnModel().getColumn(4).setPreferredWidth(85);
    }

    public Menu() {
        listMahasiswa = new ArrayList<>();
        populateList();

        mahasiswaTable.setModel(setTable());
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));
        resize();

        String[] jenisKelaminData = {"", "Laki-laki", "Perempuan"};
        jenisKelaminComboBox.setModel(new DefaultComboBoxModel<>(jenisKelaminData));

        String[] tanggalData = {"", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21",
                                "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        tanggalComboBox.setModel(new DefaultComboBoxModel<>(tanggalData));

        String[] bulanData = {"", "Januari", "Februari", "Maret", "April", "Mei", "Juni",
                              "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
        bulanComboBox.setModel(new DefaultComboBoxModel<>(bulanData));

        String[] tahunData = {"", "2000", "2001", "2002", "2003", "2004", "2005", "2006",
                              "2007", "2008", "2009", "2010", "2011", "2012", "2013",
                              "2014", "2015", "2016", "2017", "2018", "2019", "2020"};
        tahunComboBox.setModel(new DefaultComboBoxModel<>(tahunData));

        deleteButton.setVisible(false);

        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) insertData();
                else updateData();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex >= 0) deleteData();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        mahasiswaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed (MouseEvent e) {
            selectedIndex = mahasiswaTable.getSelectedRow();
            String selectedNim = mahasiswaTable.getModel().getValueAt(selectedIndex, 1).toString();
            String selectedNama = mahasiswaTable.getModel().getValueAt(selectedIndex, 2).toString();
            String selectedJenisKelamin = mahasiswaTable.getModel().getValueAt(selectedIndex, 3).toString();
            String selectedTanggalLahir = mahasiswaTable.getModel().getValueAt(selectedIndex, 4).toString();
            nimField.setText(selectedNim);
            namaField.setText(selectedNama);
            jenisKelaminComboBox.setSelectedItem(selectedJenisKelamin);
            String[] selectedTanggalLahirArray = selectedTanggalLahir.split("-");
            tanggalComboBox.setSelectedItem(selectedTanggalLahirArray[0]);
            bulanComboBox.setSelectedIndex(Integer.parseInt(selectedTanggalLahirArray[1]));
            tahunComboBox.setSelectedItem(selectedTanggalLahirArray[2]);
            addUpdateButton.setText("Update");
            deleteButton.setVisible(true);
            }
        });
    }

    public final DefaultTableModel setTable() {
        Object[] column = {"No", "NIM", "Nama", "Jenis Kelamin", "Tanggal Lahir"};
        DefaultTableModel temp = new DefaultTableModel(null, column);

        for (int i = 0; i < listMahasiswa.size(); i++) {
            Object[] row = new Object[5];
            row[0] = i + 1;
            row[1] = listMahasiswa.get(i).getNim();
            row[2] = listMahasiswa.get(i).getNama();
            row[3] = listMahasiswa.get(i).getJenisKelamin();
            row[4] = listMahasiswa.get(i).getTanggalLahir();
            temp.addRow(row);
        }

        return temp;
    }

    public void insertData() {
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        String tanggalLahir = tanggalComboBox.getSelectedItem().toString() + "-" +
                              (bulanComboBox.getSelectedIndex() < 10 ? "0" + bulanComboBox.getSelectedIndex() : String.valueOf(bulanComboBox.getSelectedIndex())) + "-" +
                              tahunComboBox.getSelectedItem().toString();

        listMahasiswa.add(new Mahasiswa(nim, nama, jenisKelamin, tanggalLahir));
        mahasiswaTable.setModel(setTable());

        clearForm();
        resize();

        System.out.println("Insert berhasil!");
        JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan!");
    }

    public void updateData() {
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        String tanggalLahir = tanggalComboBox.getSelectedItem().toString() + "-" +
                              (bulanComboBox.getSelectedIndex() < 10 ? "0" + bulanComboBox.getSelectedIndex() : String.valueOf(bulanComboBox.getSelectedIndex())) + "-" +
                              tahunComboBox.getSelectedItem().toString();

        listMahasiswa.get(selectedIndex).setNim(nim);
        listMahasiswa.get(selectedIndex).setNama(nama);
        listMahasiswa.get(selectedIndex).setJenisKelamin(jenisKelamin);
        listMahasiswa.get(selectedIndex).setTanggalLahir(tanggalLahir);

        mahasiswaTable.setModel(setTable());

        clearForm();
        resize();

        System.out.println("Update berhasil!");
        JOptionPane.showMessageDialog(null, "Data berhasil diubah!");
    }

    public void deleteData() {
        int dialogResult = JOptionPane.showConfirmDialog(null, "Hapus data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            listMahasiswa.remove(selectedIndex);
            mahasiswaTable.setModel(setTable());

            clearForm();
            resize();

            System.out.println("Delete berhasil!");
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
        }
    }

    public void clearForm() {
        nimField.setText("");
        namaField.setText("");
        jenisKelaminComboBox.setSelectedIndex(0);
        addUpdateButton.setText("Add");
        deleteButton.setVisible(false);
        selectedIndex = -1;
    }

    private void populateList() {
        listMahasiswa.add(new Mahasiswa("2203999", "Amelia Zalfa Julianti", "Perempuan", "01-01-2000"));
        listMahasiswa.add(new Mahasiswa("2202292", "Muhammad Iqbal Fadhilah", "Laki-laki", "02-02-2001"));
        listMahasiswa.add(new Mahasiswa("2202346", "Muhammad Rifky Afandi", "Laki-laki", "03-03-2002"));
        listMahasiswa.add(new Mahasiswa("2210239", "Muhammad Hanif Abdillah", "Laki-laki", "04-04-2003"));
        listMahasiswa.add(new Mahasiswa("2202046", "Nurainun", "Perempuan", "05-05-2004"));
        listMahasiswa.add(new Mahasiswa("2205101", "Kelvin Julian Putra", "Laki-laki", "06-06-2005"));
        listMahasiswa.add(new Mahasiswa("2200163", "Rifanny Lysara Annastasya", "Perempuan", "07-07-2006"));
        listMahasiswa.add(new Mahasiswa("2202869", "Revana Faliha Salma", "Perempuan", "08-08-2007"));
        listMahasiswa.add(new Mahasiswa("2209489", "Rakha Dhifiargo Hariadi", "Laki-laki", "09-09-2008"));
        listMahasiswa.add(new Mahasiswa("2203142", "Roshan Syalwan Nurilham", "Laki-laki", "10-10-2009"));
        listMahasiswa.add(new Mahasiswa("2200311", "Raden Rahman Ismail", "Laki-laki", "11-11-2010"));
        listMahasiswa.add(new Mahasiswa("2200978", "Ratu Syahirah Khairunnisa", "Perempuan", "12-12-2011"));
        listMahasiswa.add(new Mahasiswa("2204509", "Muhammad Fahreza Fauzan", "Laki-laki", "01-01-2012"));
        listMahasiswa.add(new Mahasiswa("2205027", "Muhammad Rizki Revandi", "Laki-laki", "02-02-2013"));
        listMahasiswa.add(new Mahasiswa("2203484", "Arya Aydin Margono", "Laki-laki", "03-03-2014"));
        listMahasiswa.add(new Mahasiswa("2200481", "Marvel Ravindra Dioputra", "Laki-laki", "04-04-2015"));
        listMahasiswa.add(new Mahasiswa("2209889", "Muhammad Fadlul Hafiizh", "Laki-laki", "05-05-2016"));
        listMahasiswa.add(new Mahasiswa("2206697", "Rifa Sania", "Perempuan", "06-06-2017"));
        listMahasiswa.add(new Mahasiswa("2207260", "Imam Chalish Rafidhul Haque", "Laki-laki", "07-07-2018"));
        listMahasiswa.add(new Mahasiswa("2204343", "Meiva Labibah Putri", "Perempuan", "08-08-2019"));
    }
}
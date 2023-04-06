package net.sf.dynamicreports.examples;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JasperExportManager;

/**
 * SQL 
 * 

CREATE TABLE `customers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=latin1$$
 
 	INSERT INTO `test`.`customers` (`first_name`, `last_name`, `date`) VALUES ('Ricardo', 'Mariaca', CURRENT_DATE);
	INSERT INTO `test`.`customers` (`first_name`, `last_name`, `date`) VALUES ('YONG', 'MOOK KIM', CURRENT_DATE);

 */
public class SimpleReportExample {

	public static void main(String[] args) {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jtrac_28_dec_21","root", "admin@123");
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}

		JasperReportBuilder report = DynamicReports.report();//a new report

		/**
		 * Report from database
		 * */
		report
		  .columns(
		  	Columns.column("Customer Id", "id", DataTypes.integerType())
		  		.setHorizontalAlignment(HorizontalAlignment.LEFT),
		  	Columns.column("First Name", "login_name", DataTypes.stringType()),
		  	Columns.column("Last Name", "name", DataTypes.stringType()),
		  	Columns.column("Email", "email", DataTypes.stringType())
		  		.setHorizontalAlignment(HorizontalAlignment.LEFT)
		  	)
		  .title(//title of the report
		  	Components.text("SimpleReportExample")
		  		.setHorizontalAlignment(HorizontalAlignment.CENTER))
		  .pageFooter(Components.pageXofY())//show page number on the page footer
		  .setDataSource("SELECT id, login_name, name, email FROM users", connection);

//		/**
//		 * Report from collection
//		 * */
//
//		List<String > nameList= Arrays.asList("Ram","Shyam");
//		report.setDataSource(nameList);

		try {
			report.show();//show the report
			String uploadFolderName = Paths.get("").toAbsolutePath().toString().concat("/src/test/resources");
			report.toJasperPrint();
//			JasperExportManager.exportReportToPdf(report.toJasperPrint());
			report.toPdf(new FileOutputStream(uploadFolderName+"/report.pdf"));//export the report to a pdf file
		} catch (DRException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

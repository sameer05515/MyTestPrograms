package test.p;

public class PrintMystring {
//    private static String s= "Select payroll_salary_process.employee_master_pk "
//            +"\n"+  "	from payroll_salary_process "
//            +"\n"+  "	where payroll_salary_process.current_year= ${current_year} "
//            +"\n"+  "	And payroll_salary_process.current_month= #{current_month} "
//            +"\n"+  "	AND payroll_salary_process.employee_master_pk = ${employee_master_pk} ";

    private static String s= "SELECT payroll_heads_process.employee_master_pk,payroll_heads_process.head_pk,sum(payroll_heads_process.amount) as amount,'0' as status,"
            +"\n"+  " sum(payroll_heads_process.paid_amount) as paid_amount,payroll_heads_process.head_type,${tbl_name}.head_name "
            +"\n"+  " FROM employee_master"
            +"\n"+  " Inner Join payroll_heads_process ON payroll_heads_process.employee_master_pk = employee_master.pk"
            +"\n"+  " Inner join ${tbl_name} on ${tbl_name}.pk=payroll_heads_process.head_pk"
            +"\n"+  " INNER JOIN payroll_salary_process ON payroll_salary_process.employee_master_pk = payroll_heads_process.employee_master_pk "
            +"\n"+  "	AND payroll_salary_process.current_year = payroll_heads_process.current_year "
            +"\n"+  "	AND payroll_salary_process.current_month = payroll_heads_process.current_month "
            +"\n"+  " where payroll_heads_process.process_date>#{date_from} And payroll_heads_process.process_date<=#{date_to}"
            +"\n"+  " AND payroll_heads_process.head_type=${head_type} And employee_master.pk=${employee_master_pk}"
            +"\n"+  " AND payroll_salary_process.project_salary_status = 0  "
            +"\n"+  " GROUP BY payroll_heads_process.employee_master_pk, ${tbl_name}.pk"
            +"\n"+  " ORDER BY ${tbl_name}.sequence";
    public static void main(String[] args) {
        System.out.println(s);
    }
}

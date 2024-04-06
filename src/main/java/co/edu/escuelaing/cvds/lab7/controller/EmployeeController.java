package co.edu.escuelaing.cvds.lab7.controller;

import co.edu.escuelaing.cvds.lab7.model.Employee;
import co.edu.escuelaing.cvds.lab7.repository.EmployeeRepository;
import co.edu.escuelaing.cvds.lab7.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/{id}")
    @ResponseBody
    public Employee getEmployeeById(@PathVariable String id) {
        return employeeService.getEmployee(id);
    }

    @GetMapping("/")
    @ResponseBody
    public List<Employee> getAllEmployee() {
        return employeeService.getAllEmployee();
    }
    @PostMapping("/")
    @ResponseBody
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);

    }

    @PutMapping("/employee/{id}")
    public Employee updateEmployee(@PathVariable String id, @RequestBody Employee updatedEmployee) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setFirstName(updatedEmployee.getFirstName());
                    employee.setLastName(updatedEmployee.getLastName());
                    // Set other fields as needed
                    return employeeRepository.save(employee);
                })
                .orElseGet(() -> {
                    updatedEmployee.setEmployeeId(id);
                    return employeeRepository.save(updatedEmployee);
                });
    }

    @DeleteMapping("/employee/{id}")
    public void removeEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
    }
}

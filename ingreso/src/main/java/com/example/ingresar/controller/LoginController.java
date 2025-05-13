


import com.example.ingresar.model.LoginModel;
import com.example.ingresar.repository.LoginRepository;


@RestController
@RequestMapping("/api/login")
public class LoginController {
    
    @Autowired
    private LoginService loginService;


    @GetMapping("/{idlogin}")
    public ResponseEntity<?> buscarloginporid(@PathVariable Long idlogin) {
        try {
            LoginModel loginModel = loginService.obtenerloginPorId(idUsuario);
            return ResponseEntity.ok(LoginModel);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}

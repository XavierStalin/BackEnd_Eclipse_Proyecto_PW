package ec.edu.ups.ppw.model;

import jakarta.persistence.*;

@Entity
@Table(name = "PW_TOKENS")
public class Token {
	public Token() {
	}
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // usamos long

    @Column(unique = true, length = 500)
    private String token;

    @Column(name = "token_type", length = 20)
    private String tokenType = "BEARER";

    private boolean revoked;

    private boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public boolean isRevoked() {
		return revoked;
	}

	public void setRevoked(boolean revoked) {
		this.revoked = revoked;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

    
}
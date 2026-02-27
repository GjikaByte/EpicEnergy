import { useState, useEffect } from "react";
import { ChevronRight, Layers, Zap } from "lucide-react";
import Login from "./Login";
import { useNavigate } from "react-router-dom";

function App() {
  const [showForm, setShowForm] = useState<boolean>(false);
  const [showSignup, setSignup] = useState<boolean>(false);
  const navigate = useNavigate();

  const handleClick = (
    setState: (updater: (prev: boolean) => boolean) => void,
  ): void => {
    setState((prev) => !prev);
  };
  interface RegisterFormData {
    username: string;
    nome: string;
    cognome: string;
    email: string;
    password: string;
  }

  interface RegisterResponse {
    accessToken: string;
  }

  const [formData, setFormData] = useState<RegisterFormData>({
    username: "",
    nome: "",
    cognome: "",
    email: "",
    password: "",
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    setFormData((prev) => ({
      ...prev,
      [name as keyof RegisterFormData]: value,
    }));
    console.log(formData);
  };
  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    try {
      const response = await fetch("http://localhost:3001/auth/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      if (!response.ok) {
        const errorBody = await response.json();
        console.error(
          "Errore dal backend:",
          JSON.stringify(errorBody, null, 2),
        );
        throw new Error("Errore nella registrazione");
      }

      const data: RegisterResponse = await response.json();

      localStorage.setItem("accessToken", data.accessToken);

      console.log("Registrazione completata");
      navigate("/dashboard");
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className="relative min-h-screen">
      <nav className="navbar glass flex justify-between items-center">
        <div className="nav-logo">
          <Layers color="#3b82f6" size={28} />
          Kevin<span>.io</span>
        </div>
        <div className="flex items-center gap-4">
          <a href="#login" style={{ fontSize: "0.875rem", fontWeight: 500 }}>
            Raul in
          </a>
          <button className="btn btn-glass">
            Get Started <ChevronRight size={16} />
          </button>
        </div>
      </nav>

      <main className="container hero flex flex-col items-center justify-center text-center relative">
        <h1 className="hero-title mb-6 animate-fade-in delay-200">
          Epic<span className="text-gradient">Energy</span>{" "}
          <Zap size={50}></Zap>
        </h1>

        <div
          className="form-container position-relative"
          style={{ minHeight: "100px", width: "100%", maxWidth: "500px" }}>
          {showForm && <Login />}

          {showSignup && (
            <form
              className="position-absolute top-0 start-50 translate-middle-x w-100"
              style={{ zIndex: 10 }}
              onSubmit={handleSubmit}>
              <div className="mb-3">
                <label className="form-label fw-bold">Username</label>
                <input
                  type="text"
                  className="form-control"
                  placeholder="Username"
                  name="username"
                  value={formData.username}
                  onChange={handleChange}
                  required
                />
              </div>

              <div className="row">
                <div className="col-md-6 mb-3">
                  <label className="form-label fw-bold">Name</label>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Name"
                    required
                    name="nome"
                    value={formData.nome}
                    onChange={handleChange}
                  />
                </div>
                <div className="col-md-6 mb-3">
                  <label className="form-label fw-bold">Surname</label>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Surname"
                    required
                    name="cognome"
                    value={formData.cognome}
                    onChange={handleChange}
                  />
                </div>
              </div>
              <div className="mb-3">
                <div className="mb-3">
                  <label className="form-label fw-bold">Email</label>
                  <input
                    type="email"
                    className="form-control"
                    name="email"
                    placeholder="Giangiorgio@pupo.com"
                    required
                    value={formData.email}
                    onChange={handleChange}
                  />
                </div>
                <div id="emailHelp" className="form-text">
                  We'll never share your email with anyone else.
                </div>
              </div>
              <div className="mb-3">
                <label className="form-label fw-bold">Password</label>
                <input
                  type="password"
                  className="form-control"
                  placeholder="Password"
                  name="password"
                  value={formData.password}
                  onChange={handleChange}
                />
              </div>
              <div className="mb-3 form-check">
                <input
                  type="checkbox"
                  className="form-check-input"
                  id="exampleCheck1"
                />
                <label className="form-check-label" htmlFor="exampleCheck1">
                  Our privacy policy
                </label>
              </div>
              <button type="submit" className="btn btn-primary">
                Submit
              </button>
            </form>
          )}
        </div>

        <div className="d-flex gap-4 mt-4" style={{ minHeight: "50px" }}>
          {!showForm && !showSignup && (
            <>
              <button
                className="btn btn-primary btn-large"
                onClick={() => handleClick(setShowForm)}>
                Login <Zap size={20} />
              </button>
              <button
                className="btn btn-warning btn-large"
                onClick={() => handleClick(setSignup)}>
                Signup <Zap size={20} />
              </button>
            </>
          )}
        </div>

        <div className="mt-4" style={{ minHeight: "50px" }}>
          {showForm && (
            <button
              className="btn btn-danger btn-large"
              onClick={() => handleClick(setShowForm)}>
              Go back
            </button>
          )}
          {showSignup && (
            <button
              className="btn btn-danger btn-large"
              onClick={() => handleClick(setSignup)}>
              Go back
            </button>
          )}
        </div>
      </main>

      <footer>
        <div className="footer-content">
          <div className="flex items-center gap-2">
            <Layers color="#475569" size={20} />
            <span>Andi.io</span>
          </div>
          <p>© 2026 Gianluca Platform</p>
        </div>
      </footer>
    </div>
  );
}

export default App;

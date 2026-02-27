import { useState, ChangeEvent, FormEvent } from "react";

interface LoginFormData {
  email: string;
  password: string;
}

interface LoginResponse {
  token: string;
}

function Login() {
  const [formData, setFormData] = useState<LoginFormData>({
    email: "",
    password: "",
  });

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    setFormData((prev) => ({
      ...prev,
      [name as keyof LoginFormData]: value,
    }));
  };

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    try {
      const response = await fetch("http://localhost:3001/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      if (!response.ok) {
        throw new Error("Credenziali non valide");
      }

      const data: LoginResponse = await response.json();

      localStorage.setItem("authToken", data.token);

      console.log("Login effettuato");
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <form
      className="position-absolute top-0 start-50 translate-middle-x w-100"
      style={{ zIndex: 10 }}
      onSubmit={handleSubmit}>
      <div className="mb-3">
        <label className="form-label fw-bold" >
          Email address
        </label>
        <input
          type="email"
          className="form-control"
          id="exampleInputEmail1"
          aria-describedby="emailHelp"
          name="email"
          value={formData.email}
          onChange={handleChange}
        />
        <div id="emailHelp" className="form-text">
          We'll never share your email with anyone else.
        </div>
      </div>
      <div className="mb-3">
        <label htmlFor="exampleInputPassword1" className="form-label fw-bold">
          Password
        </label>
        <input
          type="password"
          className="form-control"
          id="exampleInputPassword1"
          name="password"
          value={formData.password}
          onChange={handleChange}
        />
      </div>

      <button type="submit" className="btn btn-primary">
        Submit
      </button>
    </form>
  );
}

export default Login;

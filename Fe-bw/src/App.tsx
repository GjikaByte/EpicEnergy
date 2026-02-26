import { useState, useEffect } from "react";
import { ChevronRight, Layers, Zap } from "lucide-react";

function App() {
  const [data, setData] = useState<{ status: string } | null>(null);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState<boolean>(false);
  const [showSignup, setSignup] = useState<boolean>(false);

  const handleClick = (
    setState: (updater: (prev: boolean) => boolean) => void,
  ): void => {
    setState((prev) => !prev);
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
        
        <div className="form-container position-relative" style={{ minHeight: '400px', width: '100%', maxWidth: '500px' }}>
          {showForm && (
            <form className="position-absolute top-0 start-50 translate-middle-x w-100" style={{ zIndex: 10 }}>
              <div className="mb-3">
                <label htmlFor="exampleInputEmail1" className="form-label fw-bold">
                  Email address
                </label>
                <input
                  type="email"
                  className="form-control"
                  id="exampleInputEmail1"
                  aria-describedby="emailHelp"
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
          
          {showSignup && (
            <form className="position-absolute top-0 start-50 translate-middle-x w-100" style={{ zIndex: 10 }}>
              <div className="mb-3">
                <label className="form-label fw-bold">Username</label>
                <input
                  type="text"
                  className="form-control"
                  placeholder="Username"
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
                  />
                </div>
                <div className="col-md-6 mb-3">
                  <label className="form-label fw-bold">Surname</label>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Surname"
                    required
                  />
                </div>
              </div>
              <div className="mb-3">
                <div className="mb-3">
                  <label className="form-label fw-bold">Email</label>
                  <input
                    type="email"
                    className="form-control"
                    placeholder="Giangiorgio@pupo.com"
                    required
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


  
        <div className="d-flex gap-4 mt-4" style={{ minHeight: '50px' }}>
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

        <div className="mt-4" style={{ minHeight: '50px' }}>
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
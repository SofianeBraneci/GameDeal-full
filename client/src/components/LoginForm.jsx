import React, { Component } from "react";
import axios from "axios";
import { loginValidation } from "./utils";
import { Link } from "react-router-dom";
export default class LoginForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      password: "",
      error: "",
    };
  }

  handleUsernameChange = (e) => {
    this.setState({ username: e.target.value });
  };

  handlePasswordChange = (e) => {
    this.setState({ password: e.target.value });
  };

  handleLogin = (e) => {
    e.preventDefault();
    if (!loginValidation(this.state.username, this.state.password)) {
      this.setState({ error: "The login or the password you’ve entered is incorrect." });
      return;
    }
    if (this.state.error !== "") {
      this.setState({ error: "" });
    }
    axios.defaults.withCredentials = true
    axios
      .post(
        "http://localhost:8081/GameDeal/login",
        {},
        {
          params: {
            username: this.state.username,
            password: this.state.password,
          },
          withCredentials: true,
        }
      )
      .then((response) => {
        // we redirect from here
        console.log(response);
        const { message } = response.data;
        if (message === "user found") {
          localStorage.setItem("username", this.state.username);
          localStorage.setItem("userId", response.data.userId);
          this.props.history.push("/deals");
        } else {
          this.setState({ error: message });
        }
      })
      .catch((error) => {
        console.log(error);
        this.props.history.push("/login");
      });
  };

  render() {
    return (
      <div className="bg-image">
        <div className="container">
          <div className="d-flex align-items-center justify-content-center min-vh-100">
            <div className="row">
              <div className="col-md-6">
                <form>
                  {this.state.error != null ? (
                    <p className="error">{this.state.error}</p>
                  ) : null}
                  <div>
                    <input
                      className="form-control mb-3 input-form shadow-none"
                      type="text"
                      name="username"
                      id=""
                      placeholder="Your username"
                      required
                      onChange={this.handleUsernameChange}
                      autoComplete="off"
                    />
                  </div>
                  <div>
                    <input
                      className="form-control mb-3 input-form shadow-none"
                      type="password"
                      name="password"
                      id=""
                      placeholder="Your password"
                      required
                      onChange={this.handlePasswordChange}
                      autoComplete="off"
                    />
                  </div>
                  <div>
                    <small
                      id="emailHelp"
                      className="form-text  text-white semi-bold mb-5"
                    >
                      Not yet subscribed?
                      <Link to="/signup" className="link link-signup semi-bold">
                        <b> Sign Up </b>
                      </Link>
                      .
                    </small>
                    <br />
                    <button
                      type="submit"
                      className="btn  btn-block btn-outline-warning mt-3"
                      onClick={this.handleLogin}
                    >
                      Login
                    </button>
                  </div>
                </form>
              </div>
              <div className="col-md-6">
                <h1 className="hero-text text-center">WHERE THE FUN BEGINS</h1>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

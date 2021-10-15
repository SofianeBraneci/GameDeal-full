import axios from "axios";
import React, { Component } from "react";
import { Link } from "react-router-dom";
import { signupValidation } from "./utils";
export default class SignupForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      firstname: "",
      lastname: "",
      username: "",
      password: "",
      email: "",
      confirmPassword: "",
      error: "",
    };
  }

  handleUsernameChange = (event) => {
    this.setState({ username: event.target.value });
  };
  handlePasswordChange = (event) => {
    this.setState({ password: event.target.value });
  };
  handleFirstNameChange = (event) => {
    this.setState({ firstname: event.target.value });
  };
  handleLastNameChange = (event) => {
    this.setState({ lastname: event.target.value });
  };
  handleEmailChange = (event) => {
    this.setState({ email: event.target.value });
  };
  handleConfirmPasswordChange = (event) => {
    this.setState({ confirmPassword: event.target.value });
  };

  handleSignup = (event) => {
    event.preventDefault();
    console.log(this.state.password + " " + this.state.confirmPassword);
    axios.defaults.withCredentials = true;
    console.log(this.state.lastname);
    axios
      .post(
        "http://localhost:8081/GameDeal/signup",
        {},
        {
          params: {
            username: this.state.username,
            firstname: this.state.firstname,
            lastname: this.state.lastname,
            email: this.state.email,
            password: this.state.password,
            confirmPassword: this.state.confirmPassword,
          },
          withCredentials: true,
        }
      )
      .then((response) => {
        console.log(response);
        const { message } = response.data;
        console.log(message)
        if (message === "user signed in") {
          console.log("passed")
          localStorage.setItem("username", this.state.username);
          localStorage.setItem("userId", response.data.userId);
          this.props.history.push("/login");
        } else {
          this.setState({ error: message });
        }
      })
      .catch((err) => {
        console.log(err);
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
                  <div>
                    {this.state.error !== "" ? (
                      <p className="error">{this.state.error}</p>
                    ) : null}
                    <input
                      className="form-control mb-3 input-form shadow-none"
                      type="text"
                      name="firstname"
                      id=""
                      placeholder="First name"
                      required
                      onChange={this.handleFirstNameChange}
                      autoComplete="off"
                    />
                  </div>
                  <div>
                    <input
                      className="form-control mb-3 input-form shadow-none"
                      type="text"
                      name="lastname"
                      id=""
                      placeholder="Last name"
                      required
                      onChange={this.handleLastNameChange}
                      autoComplete="off"
                    />
                  </div>
                  <div>
                    <input
                      className="form-control mb-3 input-form shadow-none"
                      type="text"
                      name="email"
                      id=""
                      placeholder="Email"
                      required
                      onChange={this.handleEmailChange}
                      autoComplete="off"
                    />
                  </div>
                  <div>
                    <input
                      className="form-control mb-3 input-form shadow-none"
                      type="text"
                      name="username"
                      id=""
                      placeholder="Username"
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
                      placeholder="Password"
                      required
                      onChange={this.handlePasswordChange}
                      autoComplete="off"
                    />
                  </div>

                  <div>
                    <input
                      className="form-control mb-3 input-form shadow-none"
                      type="password"
                      name="confirmpassword"
                      id=""
                      placeholder="Confirm password"
                      required
                      onChange={this.handleConfirmPasswordChange}
                      autoComplete="off"
                    />
                  </div>
                  <div>
                    <small
                      id="emailHelp"
                      className="form-text  text-white semi-bold mb-5"
                    >
                      Already have an account?
                      <Link to="/login" className="link link-signup">
                         <b> Login </b>
                      </Link>
                    </small>
                    <br />
                    <button
                      type="submit"
                      className="btn  btn-block btn-outline-warning mt-3"
                      onClick={this.handleSignup}
                    >
                      Sign up
                    </button>
                  </div>
                </form>
              </div>
              <div className="col-md-6">
                <h1 className="hero-text text-center">
                  GAIN ACCESS TO THE BEST DEALS ON THE MARKET
                </h1>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

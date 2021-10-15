import axios from "axios";
import React, { Component } from "react";
import Deal from "./Deal";
import Game from "./Game";
import ShopNav from "./ShopNav";

export default class Dashboard extends Component {
  constructor(props) {
    super(props);
    this.state = {
      deals: [],
      search: "",
      sortby: "Price",
      results: [],
      searchError: "",
    }; // results for search criteria
  }

  componentDidMount() {
    axios.defaults.withCredentials = true;
    axios
      .get(
        "http://localhost:8081/GameDeal/deals",
        {},
        {
          params: {
            pageSize: 60,
            sortBy: this.state.sortby,
            withCredentials: true,
          },
        }
      )
      .then((response) => {
        this.setState({ deals: response.data.games });
        console.log(this.state.deals);
      })
      .catch((error) => {
        console.log(error);
      });
  }

  handleSortChange = (event) => {
    this.setState({ sortby: event.target.value });
    console.log(this.state.sortby);
  };

  handleLogout = () => {
    console.log("clicked");
    axios.defaults.withCredentials = true;
    axios
      .delete(
        "http://localhost:8081/GameDeal/login",
        {},
        { withCredentials: true }
      )
      .then((response) => {
        console.log(response);
        const { message } = response.data;
        if (message === "session was invalidated") {
          localStorage.removeItem("userId");
          this.props.history.push("/login");
        }
      });
  };

  handleSearchChange = (event) => {
    if (this.state.searchError)
      this.setState({ search: event.target.value, searchError: "" });
    else this.setState({ search: event.target.value });
  };

  handleSearch = () => {
    console.log("clicked");
    // this.setState({results: data})
    axios.defaults.withCredentials = true;
    axios
      .get(
        "http://localhost:8081/GameDeal/deals",
        {
          params: {
            withCredentials: true,
            title: this.state.search.replace(" ", ""),
            sortBy: this.state.sortby,
          },
        }
      )
      .then((response) => {
        console.log(response.data);
        this.setState({ results: response.data.games, search: "", searchError: "" });
      })
      .catch(() => {
        this.setState({
          searchError: "an error occured while reaching the server",
        });
      });
  };

  render() {
    return (
      <div>
        <ShopNav
          history={this.props.history}
          username={localStorage.getItem("username")}
        ></ShopNav>
        <div className="album py-5 bg-light">
          <div className="container text-center">
            <div className="space">
              <h1>OUR BEST DEALS</h1>
            </div>
            <div className="space row d-flex justify-content-center">
              <div className="col-md-3">
                <select
                  className="form-select"
                  aria-label="Default select example"
                  onChange={this.handleSortChange}
                >
                  <option defaultValue>Sort by</option>
                  <option value="Title">Title</option>
                  <option value="Price">Price</option>
                  <option value="recent">Recent</option>
                  <option value="Reviews">Rating</option>
                </select>
              </div>
              <div className="col-md-3">
                <input
                  className="form-control shadow-none"
                  type="text"
                  name=""
                  id="search-bar"
                  placeholder="Title of your favourite game"
                  onChange={this.handleSearchChange}
                />
              </div>
              <div className="col-md-3">
                <button className="btn btn-warning" onClick={this.handleSearch}>
                  Search
                </button>
              </div>
            </div>
            <div className="row ">
              <div>
                {this.state.searchError ? (
                  <p className="error">{this.state.searchError}</p>
                ) : null}
              </div>
              <div className="d-flex flex-wrap justify-content-center">
                {this.state.results.length > 0
                  ? this.state.results.map((game) => (
                      <Game
                        key={game.gameID}
                        dealId={game.dealID}
						storeId={game.storeID} 
                        imgsrc={game.thumb}
                        title={game.title}
                        salePrice={game.normalPrice}
                        
                      />
                    ))
                  : this.state.deals.map((deal) => (
                      <Deal
                        key={deal.dealID}
                        dealId={deal.dealID}
						storeId={deal.storeID} 
                        title={deal.title}
                        isOnSale={deal.isOnSale}
                        salePrice={deal.salePrice}
                        normalPrice={deal.normalPrice}
                        rating={deal.dealRating}
                        imgsrc={deal.thumb}
                        gameId={deal.gameID}
                      />
                    ))}
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

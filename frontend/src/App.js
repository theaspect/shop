import React, {Component} from 'react';
import {HashRouter as Router, Link, Route} from 'react-router-dom'
import logo from './logo.svg';
import rest from 'rest-js';
import './App.css';

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {categories: {content: []}};

        const restApi = rest('/api', {defaultFormat: ''});
        restApi.read('/categories/').then((data) => this.setState({categories: data}));
    }

    render() {
        return (
            <div className="App">
                <header className="App-header">
                    <img src={logo} className="App-logo" alt="logo"/>
                    <h1 className="App-title">Welcome to React</h1>
                </header>
                <p className="App-intro">
                    To get started, edit <code>src/App.js</code> and save to reload.
                </p>
                <Router hashType="noslash">
                    <div>
                        <ul>
                            <li><Link key="root" to="/">Home</Link></li>
                            <li><Link key="about" to="/about">About</Link></li>
                            <li><Link key="topics" to="/topics">Topics</Link></li>
                            <br/>
                            {this.state.categories.content.map((category, id) =>
                                <li key={id}><Link to={`/cat/${id}`}>{category.name}</Link></li>)}
                        </ul>

                        <hr/>

                        <Route exact path="/" component={Home}/>
                        <Route path="/about" component={About}/>
                        <Route path="/topics" component={Topics}/>
                        <Route path="/cat/:id" render={(props) => <Category categories={this.state.categories.content} id={props.match.params.id}/>}/>
                    </div>
                </Router>
            </div>
        );
    }
}

function getLinkFromCategory(category, rel) {
    return category && category.links.find(i => i.rel === rel).href
}

// function getCategoryByName(categories, name) {
//     return categories.content.find(i => i.name === name)
// }

class Category extends Component {
    constructor(props) {
        super(props);
        this.state = {items: {content: []}};
    }

    reload(props){
        if (!props.id) { return }
        const category = props.categories[props.id];
        if(!category) { return }

        rest(getLinkFromCategory(category, "items"), {defaultFormat: ''}).read("/").then((data) => {
            console.log("Category Loaded", data);
            this.setState({items: data})
        });
    }

    componentWillReceiveProps(props){this.reload(props)}
    componentDidMount(){this.reload(this.props)}

    render() {
        return (<div>
            <div>{this.id}</div>
            <ul>
                {this.state.items.content.map((item, id) => {
                    return (<li key={id}>{item.name}</li>)
                })}
            </ul>
        </div>)
    }
}

const Home = () => (
    <div>
        <h2>Home</h2>
    </div>
);

const About = () => (
    <div>
        <h2>About</h2>
    </div>
);

const Topics = ({match}) => (
    <div>
        <h2>Topics</h2>
        <ul>
            <li>
                <Link to={`${match.url}/rendering`}>
                    Rendering with React
                </Link>
            </li>
            <li>
                <Link to={`${match.url}/components`}>
                    Components
                </Link>
            </li>
            <li>
                <Link to={`${match.url}/props-v-state`}>
                    Props v. State
                </Link>
            </li>
        </ul>

        <Route path={`${match.url}/:topicId`} component={Topic}/>
        <Route exact path={match.url} render={() => (
            <h3>Please select a topic.</h3>
        )}/>
    </div>
);

const Topic = ({match}) => (
    <div>
        <h3>{match.params.topicId}</h3>
    </div>
);

export default App;

import React from "react";
import './Login.css';
import { GiThreeKeys } from "react-icons/gi";
import { Link, useMatch, useResolvedPath } from "react-router-dom";
import img1 from "../../assets/img1.jpg"

export const LoginSection = () => {
    return (
        <div className="container container-margin">
            <div className="row">
                <div className="col">
                    <div className="auth-frame">
                        <div className="auth-box">
                            <div className="auth-icon"></div>
                            <div className="auth-content">
                                <ul>
                                    <li>Proste Logowanie</li>
                                    <li>Uwierzytelnianie: hasłem</li>
                                    <li>Sesja: w cookies,</li>
                                    <li>Hasła w bazie: nieszyfrowane</li>
                                </ul>
                            </div>
                            <div className="auth-footer">
                                <Link to="/login1">Zaloguj się</Link>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="col">
                    <div className="auth-frame">
                        <div className="auth-box">
                            <div className="auth-icon"></div>
                            <div className="auth-content">
                                <ul>
                                    <li>Logowanie Basic Auth</li>
                                    <li>Uwierzytelnianie: hasłem,</li>
                                    <li>Sesja: local storage,</li>
                                    <li>Hasła w bazie: szyfrowane</li>
                                </ul>
                            </div>
                            <div className="auth-footer">
                                <button>Zaloguj się</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="col">
                <div className="auth-frame">
                        <div className="auth-box">
                            <div className="auth-icon"></div>
                            <div className="auth-content">
                                <ul>
                                    <li>Logowanie OAuth</li>
                                    <li>Uwierzytelnianie: hasło lub token,</li>
                                    <li>Sesja: Cookies token,</li>
                                    <li>Hasła w bazie: haszowane</li>
                                </ul>
                            </div>
                            <div className="auth-footer">
                                <button>Zaloguj się</button>
                            </div>
                        </div>
                    </div> 
                </div>
            </div>

            <div className="row-margin">


            <div className="row">
                <div className="col">
                <div className="auth-frame">
                        <div className="auth-box">
                            <div className="auth-icon"></div>
                            <div className="auth-content">
                                <ul>
                                    <li>Uwierzytelnianie: hasło jednorazow</li>
                                    <li>Protokół: HTTP,</li>
                                    <li>Sesja: w cookies niezabezpieczona,</li>
                                    <li>Hasła w bazie: szyfrowane</li>
                                </ul>
                            </div>
                            <div className="auth-footer">
                                <button>Zaloguj się</button>
                            </div>
                        </div>
                    </div> 
                </div>
                
                <div className="col">
                <div className="auth-frame">
                        <div className="auth-box">
                            <div className="auth-icon"></div>
                            <div className="auth-content">
                                <ul>
                                    <li>Uwierzytelnianie: dwupoziomowe</li>
                                    <li>Protokół: HTTPS,</li>
                                    <li>Sesja: Local Storage</li>
                                    <li>Hasła w bazie: haszowane</li>
                                </ul>
                            </div>
                            <div className="auth-footer">
                                <button>Zaloguj się</button>
                            </div>
                        </div>
                    </div> 
                </div>
                <div className="col">
                <div className="auth-frame">
                        <div className="auth-box">
                            <div className="auth-icon"></div>
                            <div className="auth-content">
                                <ul>
                                    <li>Uwierzytelnianie: dwupoziomowe</li>
                                    <li>Protokół: HTTPS,</li>
                                    <li>Sesja: Local Storage zabezpieczone</li>
                                    <li>Hasła w bazie: haszowane</li>
                                </ul>
                            </div>
                            <div className="auth-footer">
                                <button>Zaloguj się</button>
                            </div>
                        </div>
                    </div> 
                </div>
            </div>
            </div>
        </div>
    )
}
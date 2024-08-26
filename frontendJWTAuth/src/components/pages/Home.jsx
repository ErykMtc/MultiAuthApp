import './Home.css'
import { Link } from "react-router-dom";
import { FcReading } from "react-icons/fc";
import { FcExpired } from "react-icons/fc";
import { FcSettings } from "react-icons/fc";
import { FcEngineering } from "react-icons/fc";
import { GrTestDesktop } from "react-icons/gr";

export const Home = () => {
    return (
        <>
            <div className="first-section">
                <div>
                    <p>Testuj już dziś!</p>
                    <Link to={'/login'}>Login</Link>
                </div>
            </div>
            <div className="second-section">
                <div className='container'>
                    <div className='row'>
                        <div className='col center-col'>
                            <div className='second-section-box'>
                                <div className='second-section-shape'>
                                    <FcReading />
                                </div>
                                <div className='second-section-content'>
                                    Sprawdzaj zabiezpieczenia wybierając odpowiednie opcje logowania i rejestracji
                                </div>
                            </div>
                        </div>
                        <div className='col center-col'>
                            <div className='second-section-box'>
                                <div className='second-section-shape'>
                                    <GrTestDesktop />
                                </div>
                                <div className='second-section-content'>
                                    Testuj poszczególne zabezpieczenia i użyte technologie
                                </div>
                            </div>
                        </div>
                        <div className='col center-col'>
                            <div className='second-section-box'>
                                <div className='second-section-shape'>
                                    <FcEngineering />
                                </div>
                                <div className='second-section-content'>
                                    Wdrażaj i ulepszaj zabezpieczenia ochrony uwierzytelniania i autoryzacji
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div className="third-section">
                <h1>Wprowadź dane</h1>
                <div className='third-section-content'>
                    Aby móc wprowadzić dane należy się zalogować
                </div>
            </div>
        </>
    )
}